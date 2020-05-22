<%--
  Created by IntelliJ IDEA.
  User: stefa
  Date: 08.06.2019
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper">
    <table class="table table-hover table-bordered table-striped table-smc" id="myTable">
        <thead class="bg-cl white-text">
        <tr>
            <th class="txt-col" scope="col"># ID</th>
            <th class="txt-col" scope="col">First Name</th>
            <th class="txt-col" scope="col">Last Name</th>
            <th class="txt-col" scope="col">Employment Date</th>
            <th class="txt-col" scope="col">Salary</th>
            <th class="txt-col" scope="col">Supervisor ID</th>
            <th class="txt-col" scope="col">Branch</th>
            <th class="col-xs-1 text-center"></th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>

<!-- Modal -->
<div id="myModal" class="modal">
    <div class="modal-content e-modal-content modal-anim center-al">
        <div class="cl-fl-right cls-btn">
            <span class="cl" id="cl-btn">&times;</span>
        </div>

        <div class="center-al" id="formModal">
            <span class="fm-title" id="modalTitle">Branch</span>
            <span id="error-msg" style="margin-bottom: 5px;"></span>
            <form class="form" id="branchForm">
                <div class="modal-grid">
                    <div id="firstName-container">
                        <label class="fm-item-ol fm-label fm-item-txt-al" for="firstName">First Name</label>
                        <input class="fm-item-ol fm-item-txt-al input-field" id="firstName" type="text" name="firstName" placeholder="Enter first name" required>
                    </div>
                    <div id="lastName-container">
                        <label class="fm-item-ol fm-label fm-item-txt-al" for="lastName">Last Name</label>
                        <input class="fm-item-ol fm-item-txt-al input-field" id="lastName" type="text" name="lastName" placeholder="Enter last name" required>
                    </div>
                    <div id="employee-empDate-container">
                        <label class="fm-item-ol fm-label fm-item-txt-al" for="empDate">Employment date</label>
                        <input class="fm-item-ol fm-item-txt-al input-field" id="empDate" type="date" name="empDate" value="" min="" max="" required>
                    </div>
                    <div id="salary-container">
                        <label class="fm-item-ol fm-label fm-item-txt-al" for="salary">Salary</label>
                        <input class="fm-item-ol fm-item-txt-al input-field" id="salary" type="number" name="salary" placeholder="Enter salary" value="" min="10000" max="1000000" required>
                    </div>
                    <div id="supervisor-container">
                        <label class="fm-item-ol fm-label fm-item-txt-al sb-lb" for="employee">Supervisor</label>
                        <select class="sl-b e-sl-b" id="employee" name="supervisor" size="7"></select>
                    </div>
                    <div id="branch-container">
                        <label class="fm-item-ol fm-label fm-item-txt-al sb-lb" for="branch">Branch</label>
                        <select class="sl-b e-sl-b" id="branch" name="branch" size="7" required></select>
                    </div>
                </div>
                <div class="btn-wrapper">
                    <input class="submit pop-up-submit" type="submit" value="Confirm">
                </div>
            </form>
        </div>

        <div class="pop-up center-al" id="confirmModal">
            <span id="confirm-msg"></span>
            <div class="btn-wrapper">
                <button class="submit pop-up-submit" id="confirm-btn">Confirm</button>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/employee/empTable.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/employee/fetchDataSelectBranch.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/fetchDataSelectEmployee.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/employee/openModalEmployee.js"></script>
