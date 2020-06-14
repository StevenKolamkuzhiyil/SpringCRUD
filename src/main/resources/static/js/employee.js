const ajax_url = '/api/employees/';
const ajax_url_add = ajax_url + 'create';

var today = new Date().toISOString().split('T')[0];
const defaultFormData = {id: "", firstName: "", lastName: "", email: "", empDate: today, salary: "", supervisorId: "", branchId: "", role: ""};
const modal_for = 'Employee';

function initSelect() {
    fire_ajax('/api/branches', 'GET', {}, setSelect('#modalScrollable form #branchId', 'Select Branch', filterBranchIdAndName), log);
    fire_ajax('/api/employees/roles', 'GET', {}, setSelectByArray('#modalScrollable form #role', 'Select Role'), log);
}

function setSelectByArray(select, defaultOptionText) {
    return (json) => {
        var dropdown = $(select);
        dropdown.empty();
        dropdown.append('<option value="">'+defaultOptionText+'</option>');
        dropdown.prop('selectedIndex', 0);

        json.forEach(e => {
            dropdown.append($('<option></option>').attr('value', e).text(e));
        });
    }
}

function setModal(data) {
    $('#modalScrollable form #id').val(data.id);
    $('#modalScrollable form #firstName').val(data.firstName);
    $('#modalScrollable form #lastName').val(data.lastName);
    $('#modalScrollable form #email').val(data.email);
    $('#modalScrollable form #empDate').val(data.empDate);
    $('#modalScrollable form #salary').val(data.salary);
    $('#modalScrollable form #supervisorId').val(data.supervisorId);
    $('#modalScrollable form #branchId').val(data.branchId);
    $('#modalScrollable form #role').val(data.role);
}

function initModalAdd(table, tr) {
    var data = table.rows().data().toArray();
    setSelect('#modalScrollable form #supervisorId', 'Select Supervisor', filterEmployeeIdAndName)(data);
    today = new Date().toISOString().split('T')[0];
    $("#empDate").attr('min', today);
    $("#email").prop('readonly', false);
    setModal(defaultFormData);
}

function initModalEdit(table, tr) {
    $("#empDate").removeAttr('min');
    $("#email").prop('readonly', true);
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
                    $('#modalScrollable form #emailError'),
                    $('#modalScrollable form #empDateError'),
                    $('#modalScrollable form #salaryError'),
                    $('#modalScrollable form #supervisorIdError'),
                    $('#modalScrollable form #branchIdError'),
                    $('#modalScrollable form #roleError')
                  ];
    errDivs.forEach(d => {
        d.text('');
        if (!d.hasClass('d-none')) {
            d.addClass('d-none');
        }
    });
}