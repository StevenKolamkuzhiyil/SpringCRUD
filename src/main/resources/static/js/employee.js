const ajax_url = '/api/employees/';
const ajax_url_add = ajax_url + 'create';
const table_columns = [ { data: 'id' },
                          { data: 'firstName' },
                          { data: 'lastName' },
                          { data: 'empDate' },
                          { data: 'salary' },
                          { data: 'supervisorId' },
                          { data: 'branchId' },
                          {
                              data: null,
                              defaultContent: '<button type="button" class="edit-btn btn btn-info btn-sm mr-2" data-toggle="modal" data-target="#modalScrollable"><i class="far fa-edit"></i></button>'+
                                              '<button type="button" class="rem-btn btn btn-danger btn-sm ml-2" data-toggle="modal" data-target="#modalScrollable"><i class="far fa-trash-alt"></i></button>'
                      } ];
var today = new Date().toISOString().split('T')[0];
const defaultFormData = {id: "", firstName: "", lastName: "", empDate: today, salary: "", supervisorId: "", branchId: ""};
const modal_for = 'Employee';

function initSelect() {
    fire_ajax('/api/branches', 'GET', {}, setSelect('#modalScrollable form #branchId', 'Select Branch', filterBranchIdAndName));
}

function setModal(data) {
    $('#modalScrollable form #id').val(data.id);
    $('#modalScrollable form #firstName').val(data.firstName);
    $('#modalScrollable form #lastName').val(data.lastName);
    $('#modalScrollable form #empDate').val(data.empDate);
    $('#modalScrollable form #salary').val(data.salary);
    $('#modalScrollable form #supervisorId').val(data.supervisorId);
    $('#modalScrollable form #branchId').val(data.branchId);
}

function initModalAdd(table, tr) {
    var data = table.rows().data().toArray();
    setSelect('#modalScrollable form #supervisorId', 'Select Supervisor', filterEmployeeIdAndName)(data);
    today = new Date().toISOString().split('T')[0];
    $("#empDate").attr('min', today);
    setModal(defaultFormData);
}

function initModalEdit(table, tr) {
    $("#empDate").removeAttr('min');
    var rowData = table.row(tr).data();
    var data = table.rows().data().toArray().filter(e => e !== rowData);
    setSelect('#modalScrollable form #supervisorId', 'Select Supervisor', filterEmployeeIdAndName)(data);
    setModal(rowData);
    return rowData;
}

function resetErrorDivs() {
    var errDivs = [
                    $('#modalScrollable form #firstNameError'),
                    $('#modalScrollable form #lastNameError'),
                    $('#modalScrollable form #empDateError'),
                    $('#modalScrollable form #salaryError'),
                    $('#modalScrollable form #supervisorIdError'),
                    $('#modalScrollable form #branchIdError')
                  ];
    errDivs.forEach(d => {
        d.text('');
        if (!d.hasClass('d-none')) {
            d.addClass('d-none');
        }
    });
}