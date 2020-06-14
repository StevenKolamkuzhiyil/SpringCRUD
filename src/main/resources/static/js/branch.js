const ajax_url = '/api/branches/';
const ajax_url_add = ajax_url + 'create';

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