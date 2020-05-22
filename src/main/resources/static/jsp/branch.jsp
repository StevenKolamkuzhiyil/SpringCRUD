<%--
Created by IntelliJ IDEA.
User: stefa
Date: 05.06.2019
Time: 11:09
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper">
    <table class="table table-hover table-bordered table-striped table-smc" id="myTable">
        <thead class="bg-cl white-text">
        <tr>
            <th class="txt-col" scope="col"># ID</th>
            <th class="txt-col" scope="col">Branch Name</th>
            <th class="txt-col" scope="col">Manager</th>
            <th class="txt-col" scope="col">Manager start date</th>
            <th class="col-xs-1 text-center"></th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>


<!-- Modal -->
<div id="myModal" class="modal">
    <div class="modal-content modal-anim center-al">
        <div class="cl-fl-right cls-btn">
            <span class="cl" id="cl-btn">&times;</span>
        </div>

        <div class="pop-up center-al" id="formModal">
            <span class="fm-title" id="modalTitle">Branch</span>
            <span id="error-msg" style="margin-bottom: 5px;"></span>
            <form class="form" id="branchForm">
                <div id="name-container">
                    <label class="fm-label fm-item-ol fm-item-txt-al" for="name">* Branch Name</label>
                    <input class="fm-item-ol fm-item-txt-al input-field" id="name" type="text" name="name" placeholder="Enter branch name" required>
                </div>
                <div class="f-container" id="manager-container" style="margin-bottom: 0;">
                    <label class="fm-label fm-item-ol fm-item-txt-al sb-lb" for="employee">Manager</label>
                    <select class="sl-b" id="employee" name="manager" size="7"></select>
                </div>
                <div id="manager-empDate-container">
                    <label class="fm-label fm-item-ol fm-item-txt-al" for="mgrDate">Manager employment date</label>
                    <input class="fm-item-ol fm-item-txt-al input-field" id="mgrDate" type="date" name="mgrDate" value="" min="" max="">
                </div>
                <div class="btn-wrapper">
                    <input class="submit pop-up-submit" type="submit" value="Confirm">
                </div>
            </form>
        </div>

        <div class="pop-up center-al" id="deleteModal">
            <span id="del-msg"></span>
            <div class="btn-wrapper">
                <button class="submit pop-up-submit" id="del-confirm">Confirm</button>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/date.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/branch/branchTable.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/fetchDataSelectEmployee.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/branch/disableDate.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/branch/openModalBranch.js"></script>
