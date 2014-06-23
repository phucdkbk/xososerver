<%--
    Document   : updateResult
    Created on : Jun 23, 2014, 9:28:56 AM
    Author     : phucdk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật kết quả</title>
        <link rel="stylesheet" href="share/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.css">        
        <script src="share/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js"></script>
        <script src="share/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js"></script>
        <script>
            $(function() {
                $("#datepicker").datepicker();
            });

            showResult = function() {
                //alert(document.getElementsByName('date')[0].value);
                var date = document.getElementsByName('date')[0].value;
                location.href = 'UpdateResult?type=show&date=' + date;
            }
        </script>
        <style type="text/css">
            .resultTable {
                width: 50%;
            }
            .resultTable td input{
                width: 99%;
            }
        </style>
    </head>
    <body>
        <h1>Cập nhật kết quả</h1>
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
        <form action="UpdateResult" id="updateForm">
            <input type="hidden" name="currentDate" value="${date}"/>
            <input type="hidden" name="type" value="update"/>
            <table border="1" class="resultTable">
                <tr>
                    <td>
                        Giải đặt biệt
                    </td>
                    <td style="width: 70%">
                        <input type="text" name="giaiDB" value="${resultObject.giaiDB}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Giải nhất
                    </td>
                    <td>
                        <input type="text" name="giaiNhat" value="${resultObject.giaiNhat}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Giải nhì
                    </td>
                    <td>
                        <input type="text" name="giaiNhi" value="${resultObject.giaiNhi}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Giải ba
                    </td>
                    <td>
                        <input type="text" name="giaiBa" value="${resultObject.giaiBa}"/>
                    </td>
                </tr>

                <tr>
                    <td>
                        Giải tư
                    </td>
                    <td>
                        <input type="text" name="giaiTu" value="${resultObject.giaiTu}"/>
                    </td>
                </tr>

                <tr>
                    <td>
                        Giải năm
                    </td>
                    <td>
                        <input type="text" name="giaiNam" value="${resultObject.giaiNam}"/>
                    </td>
                </tr>

                <tr>
                    <td>
                        Giải sáu
                    </td>
                    <td>
                        <input type="text" name="giaiSau" value="${resultObject.giaiSau}"/>
                    </td>
                </tr>

                <tr>
                    <td>
                        Giải bảy
                    </td>
                    <td>
                        <input type="text" name="giaiBay" value="${resultObject.giaiBay}"/>
                    </td>
                </tr>
            </table>
            <button type="submit" form="updateForm">
                Cập nhật thông tin
            </button>
        </form>


        <a href="index.jsp">Trang chủ</a>
        <a href="ShowResult">Xem kết quả</a>
        <a href="updateResult.jsp">Nhập kết quả</a>
    </body>
</html>
