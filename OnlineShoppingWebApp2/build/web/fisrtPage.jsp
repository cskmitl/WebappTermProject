<%@page import="java.util.Iterator"%>
<%@page import="model.Products"%>
<%@page import="model.ProductsTable" %>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Show Employee</title>
    <script>
            window.addEventListener('DOMContentLoaded', function() {
            var checkboxes = document.querySelectorAll('input[type="checkbox"]');

            checkboxes.forEach(function(checkbox) {
                checkbox.addEventListener('change', function() {
                var input = document.querySelector("[name='quantity" + this.value + "']");
                input.readOnly = !this.checked;
                if (!this.checked) {
                    input.value = ""; 
                }
            });
        });
    });
    </script>
</head>
<jsp:useBean id="mov" class="model.Products" scope="request"/>
<%
    List<Products> movList = ProductsTable.findAllProducts();
    Iterator<Products> itr = movList.iterator();
%>
<body>
    <form name="addToCartForm" action="ADMC" method="post">
        <center>
            <h1>DVD Catalog</h1>
            <table border="1">
                <tr>
                    <th>DVD Names</th>
                    <th>Rate</th>
                    <th>Year</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
                <%
                    while(itr.hasNext()) {
                        mov = itr.next();
                %>
                <tr>
                    <td>
                        <input type="checkbox" name="selectedMovies" value="<%= mov.getId() %>" onchange="toggleInput(this)">
                        <%= mov.getMovie() %>
                    </td>
                    <td><%= mov.getRating() %></td>
                    <td><%= mov.getYearcreate() %></td>
                    <td><%= mov.getPrice() %></td>
                    <td>
                        <input type="text" name="quantity<%= mov.getId() %>" value="" size="10" min="0" readonly />
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <br>
            <button>Add To Cart</button>
        </center>
    </form>
</body>
</html>
