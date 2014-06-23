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
                //$("#datepicker").datepicker();
                $("#datepicker").datepicker({dateFormat: 'dd/mm/yy'});
            });

//            $(function() {
//                $("#datepicker").datepicker({dateFormat: "yy-mm-dd"}).val();
//            });

            showResult = function() {
                //alert(document.getElementsByName('date')[0].value);
                var date = document.getElementsByName('date')[0].value;
                location.href = 'ShowResult?date=' + date;
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
                    <p>Date: <input type="text" id="datepicker" name="date" value="${date}"></p>
                </td>
                <td>
                    <button onclick="showResult();">Xem</button>
                </td>
            </tr>
        </table>
        <b>Kết quả ngày ${date}:</b>
        <table border="1" class="resultTable">
            <tr>
                <td>
                    Giải đặt biệt
                </td>
                <td>
                    ${resultObject.giaiDB}
                </td>
            </tr>
            <tr>
                <td>
                    Giải nhất
                </td>
                <td>
                    ${resultObject.giaiNhat}
                </td>
            </tr>
            <tr>
                <td>
                    Giải nhì
                </td>
                <td>
                    ${resultObject.giaiNhi}
                </td>
            </tr>
            <tr>
                <td>
                    Giải ba
                </td>
                <td>
                    ${resultObject.giaiBa}
                </td>
            </tr>

            <tr>
                <td>
                    Giải tư
                </td>
                <td>
                    ${resultObject.giaiTu}
                </td>
            </tr>

            <tr>
                <td>
                    Giải năm
                </td>
                <td>
                    ${resultObject.giaiNam}
                </td>
            </tr>

            <tr>
                <td>
                    Giải sáu
                </td>
                <td>
                    ${resultObject.giaiSau}
                </td>
            </tr>

            <tr>
                <td>
                    Giải bảy
                </td>
                <td>
                    ${resultObject.giaiBay}
                </td>
            </tr>
        </table>

        <a href="index.jsp">Trang chủ</a>
        <a href="ShowResult">Xem kết quả</a>
        <a href="updateResult.jsp">Nhập kết quả</a>
    </body>
</html>
