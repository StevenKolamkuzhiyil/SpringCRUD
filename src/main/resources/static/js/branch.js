const ajax_url = '/api/branches/';
const ajax_url_add = ajax_url + 'create';
const table_columns = [ { data: 'id' },
                          { data: 'branchName' },
                          { data: 'managerId' },
                          { data: 'mgrStartDate' },
                          {
                              data: null,
                              defaultContent: '<button type="button" class="edit-btn btn btn-info btn-sm mr-2" data-toggle="modal" data-target="#modalScrollable"><i class="far fa-edit"></i></button>'+
                                              '<button type="button" class="rem-btn btn btn-danger btn-sm ml-2" data-toggle="modal" data-target="#modalScrollable"><i class="far fa-trash-alt"></i></button>'
                      } ];
var today = new Date().toISOString().split('T')[0];
const defaultFormData = {id: "", branchName: "", managerId: "", mgrStartDate: today};
const modal_for = 'Branch';

function initSelect() {
    fire_ajax('/api/employees/', 'GET', {}, setSelect('#modalScrollable form #managerId', 'Select Manager', filterEmployeeIdAndName));
}

function setModal(data) {
    $('#modalScrollable form #id').val(data.id);
    $('#modalScrollable form #branchName').val(data.branchName);
    $('#modalScrollable form #managerId').val(data.managerId);
    $('#modalScrollable form #mgrStartDate').val(data.mgrStartDate);
}

function initModalAdd(table, tr) {
    today = new Date().toISOString().split('T')[0];
    $("#mgrStartDate").attr('min', today);
    setModal(defaultFormData);
}

function initModalEdit(table, tr) {
    $("#mgrStartDate").removeAttr('min');
    var rowData = table.row(tr).data();
    setModal(rowData);
    return rowData;
}

function resetErrorDivs() {
    var errDivs = [
                    $('#modalScrollable form #branchNameError'),
                    $('#modalScrollable form #managerIdError'),
                    $('#modalScrollable form #mgrStartDateError')
                  ];
    errDivs.forEach(d => {
        d.text('');
        if (!d.hasClass('d-none')) {
            d.addClass('d-none');
        }
    });
}