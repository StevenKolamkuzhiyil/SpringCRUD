$(document).ready(function() {
    const table = $('#datatable').DataTable( {
        dom: "<'row'<'col-4 mg-l'<'ml-md-2'l>><'col-8 mg-r'<'mr-md-2'f>>>" +
             "<'row'<'col-12'tr>>" +
             "<'row align-items-center'<'col-9'p><'#btn-wp.col-3 mg-r'>>",
        columnDefs: [ { orderable: false, targets: -1 } ],
        lengthMenu: [ [10, 25, 50, -1], [10, 25, 50, 'All'] ],
        language: {
            lengthMenu: '_MENU_',
            loadingRecords: 'Loading...',
            processing:     'Processing...',
            search:         '',
            zeroRecords:    'No matching records found',
            paginate: {
              "first":      '<i class="fas fa-angle-double-left"></i>',
              "last":       '<i class="fas fa-angle-double-right"></i>',
              "next":       '<i class="fas fa-chevron-right"></i>',
              "previous":   '<i class="fas fa-chevron-left"></i>'
            }
        },
        ajax: {
            url: ajax_url,
            contentType: 'application/json',
            type: 'GET',
            dataSrc: ''
        },
        columns: table_columns,
        rowId: 'id',
        order: [[0, 'asc']],
        rowReorder: {
            selector: 'td:nth-child(1)'
        },
        responsive: {
            details: {
                renderer: function ( api, rowIdx, columns ) {
                    var data = $.map( columns, function ( col, i ) {
                        var row = '';
                        if ((columns.length - 1) !== i ) {
                            if (col.hidden) {
                                row = '<tr data-dt-row="'+col.rowIndex+'" data-dt-column="'+col.columnIndex+'">'+
                                          '<td class="border-left-0 border-right-0 wrap">'+col.title+'</td> '+
                                          '<td class="border-left-0 border-right-0 wrap">'+col.data+'</td>'+
                                      '</tr>';
                            }
                        } else {
                            if (col.hidden) {
                                row = '<tr data-dt-row="'+col.rowIndex+'" data-dt-column="'+col.columnIndex+'">'+
                                          '<td class="border-left-0 border-right-0 border-bottom-0 pt-2 wrap">'+col.data+'</td>'+
                                      '</tr>';
                            }
                        }
                        return row;

                    } ).join('');

                    return data ?
                        $('<table class="w-100 my-1"/>').append( data ) :
                        false;
                }
            }
        }
    } );

    styleDataTable();
    initSelect();
    addButtonClick(table);
    editButtonClick(table);
    removeButtonClick(table);

    $('#modalScrollable form #submit').on('click', function(event) {
        event.preventDefault();
        var json = method !== 'DELETE' ? objFromFormData($(this).closest('form')) : {};
        resetErrorDivs();
        fire_ajax(url, method, json, updateTable(table), onValidationError);
    });

    $(window).on('hidden.bs.modal', function () {
        resetErrorDivs();
        $('#modalScrollable form').trigger("reset");
    });

} );

var $tr = null;
var url = '';
var method = 'GET';

function log(value) {
    return console.log(value);
}

function updateTable(table) {
    return (json) => {
        log(json);
        if ($tr === null) {
            table.row.add(json).draw( false );
        } else {
            if (method !== 'DELETE') {
                table.row($tr).data(json).draw();
            } else {
                if (typeof json !== 'undefined' && json !== [] && json.length > 0) {
                    json.forEach(e => {
                        table.row('#'+e.id).data(e).draw();
                    });
                }
                table.row($tr).remove().draw();
            }
        }
        $('#modalScrollable').modal('toggle');
    }
}

function objFromFormData(form) {
    var data = form.serializeArray();
    var json = {};
    data.forEach(e => {
        json[e['name']] = e['value'] !== "" ? e['value'] : null;
    });
    return json;
}

function styleDataTable() {
    $('.dataTables_filter').addClass('float-right');
    $('.dataTables_filter input').addClass('form-control form-control-sm');
    $('.dataTables_filter input').css('width','calc(100% - 0.5em)');
    $('.dataTables_filter input').attr('placeholder', 'Search');

    $('.dataTables_length').addClass('float-left');
    $('.dataTables_length select').addClass('form-control form-control-sm');

    $('.dataTables_paginate').addClass('mt-0 float-left');

    var add_btn = '<button type="button" class="btn btn-outline-primary float-right mr-md-2" data-toggle="modal" id="add-btn" data-target="#modalScrollable">+</button>';
    $('#btn-wp').append(add_btn);
}


function addButtonClick(table) {
    $('#add-btn').on('click', function() {
        switchClasses('#edit', 'd-none', 'd-block');
        switchClasses('#remove', 'd-block', 'd-none');
        setModalTitleAndSubmitButtonText('Add '+ modal_for, 'Save Changes');
        initModalAdd(table, null);

        $tr = null;
        url = ajax_url_add;
        method = 'POST';
    });
}

function editButtonClick(table) {
    $("#datatable").on('click','.edit-btn', function () {
        switchClasses('#edit', 'd-none', 'd-block');
        switchClasses('#remove', 'd-block', 'd-none');
        setModalTitleAndSubmitButtonText('Edit ' + modal_for, 'Save Changes');
        $tr = $(this).closest('tr');
        var rowData = initModalEdit(table, $tr);
        var id = (typeof rowData) !== 'undefined' ? rowData.id : '';
        url = ajax_url + id;
        method = 'PUT';
    });
}

function removeButtonClick(table) {
    $("#datatable").on('click','.rem-btn', function () {
        switchClasses('#edit', 'd-block', 'd-none');
        switchClasses('#remove', 'd-block', 'd-none');
        $tr = $(this).closest('tr');
        var rowData = table.row($tr).data();
        var id = (typeof rowData) !== 'undefined' ? rowData.id : '';
        setModalTitleAndSubmitButtonText('Remove ' + modal_for + ' with ID ' + id, 'Remove');
        setModal(defaultFormData);

        url = ajax_url + id;
        method = 'DELETE';

        fire_ajax(url+'/delete', 'GET', {}, listAffectedEntries, log);
    });
}

function switchClasses(elementId, class1, class2) {
    $(elementId).removeClass(class1);
    if (!$(elementId).hasClass(class2)) {
        $(elementId).addClass(class2);
    }
}

function setModalTitleAndSubmitButtonText(title, submitText) {
    $('#modalScrollable #modalTitle').text(title);
    $('#modalScrollable #submit').text(submitText);
}

function setSelect(select, defaultOptionText, filterData) {
    return (json) => {
        var dropdown = $(select);
        dropdown.empty();
        dropdown.append('<option value="">'+defaultOptionText+'</option>');
        dropdown.prop('selectedIndex', 0);

        json.forEach(e => {
            var data = filterData(e);
            dropdown.append($('<option></option>').attr('value', data.id).text(data.name));
        });
    }
}

function filterBranchIdAndName(branch) {
    return {id: branch.id, name: branch.branchName};
}

function filterEmployeeIdAndName(employee) {
    return {id: employee.id, name: employee.firstName + ' ' + employee.lastName};
}

function listAffectedEntries(json) {
    console.log(json);
    var $removeContent = $('#remove dl');
    $removeContent.empty();
    $.each(json, function(key, value) {
        if (json[key] !== [] && json[key].length > 0) {
            $removeContent.append('<dt>'+'Affected '+key+'</dt>');
            json[key].forEach(e => {
                var data = {id: '', name: ''};
                if (key === 'Branches') {
                    data = filterBranchIdAndName(e);
                } else if (key === 'Employees') {
                    data = filterEmployeeIdAndName(e);
                }
                $removeContent.append('<dd>- ID: ' + data.id + ' | Name: ' + data.name + '</dd>');
            });
        }
    });

    if ( $removeContent.children().length > 0 ) {
        switchClasses('#remove', 'd-none', 'd-block');
    }
}

function onValidationError(json) {
    console.log(json);
    try {
        var fieldErr = json.object.fieldErrors;
        $.each(fieldErr, function(key, val) {
            var accessor = '#modalScrollable form #'+key+'Error';
            $(accessor).text(val);
            $(accessor).removeClass('d-none');
        });
    } catch (ex) {}
}

function fire_ajax(link, method, sendData, onSuccess, onError) {
    $.ajax({
        url: link,
        type: method,
        data: sendData,
        dataType: "json",
        accepts: {
            json: "application/json, text/javascript"
        },
        success: function(json, status, xhr) {
            onSuccess(json);
        },
        error: function(error, status, xhr) {
            var err = error.responseText;
            try {
                err = JSON.parse(err);
                onError(err);
            } catch (e) {
                console.log("Parsing failed");
                console.log(err);
            }
        }
    });
}