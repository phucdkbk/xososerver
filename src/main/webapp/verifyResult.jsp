<%--
    Document   : showResult
    Created on : Jun 23, 2014, 9:29:31 AM
    Author     : phucdk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xem ket qua</title>
        <link rel="stylesheet" href="share/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.css">
        <script src="share/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js"></script>
        <script src="share/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js"></script>
        <script>
            $(function() {
                $("#datepickerFromDate").datepicker({dateFormat: 'dd/mm/yy'});
            });

            $(function() {
                $("#datepickerToDate").datepicker({dateFormat: 'dd/mm/yy'});
            });

            showResult = function() {
                //alert(document.getElementsByName('date')[0].value);
                var fromDate = document.getElementsByName('fromDate')[0].value;
                var toDate = document.getElementsByName('toDate')[0].value;
                location.href = 'VerifyResult?fromDate=' + fromDate + '&toDate=' + toDate;
            };
        </script>
        <style type="text/css">
            .resultTable {
                width: 50%;
            }
            .resultTable td{
                width: 50%;
            }
        </style>
    </head>
    <body>
        <h1>Xem kết quả</h1>
        <table>
            <tr>
                <td>
                    <p>From date: <input type="text" id="datepickerFromDate" name="fromDate" value="${fromDate}"></p>
                </td>
                <td>
                    <p>To date: <input type="text" id="datepickerToDate" name="toDate"  value="${fromDate}"></p>
                </td>
                <td>
                    <button onclick="showResult();">Kiểm tra</button>
                </td>
            </tr>
        </table>
        <b>Danh sách những ngày dữ liệu lỗi:</b>
        <br />
        ${errorDate}
        <br/>
        <br/>
        <a href="index.jsp">Trang chủ</a>
        <a href="ShowResult">Xem kết quả</a>
        <a href="updateResult.jsp">Nhập kết quả</a>
    </body>
</html>
