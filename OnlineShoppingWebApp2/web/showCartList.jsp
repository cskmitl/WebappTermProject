<%@page import="model.ShoppingcartPK"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Shoppingcart"%>
<%@page import="model.ShoppingcartTable"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Show Employee</title>
</head>
<jsp:useBean id="movCart" class="model.Shoppingcart" scope="request"/>
<%
    ShoppingcartPK cartPK = (ShoppingcartPK) request.getSession().getAttribute("cartPK");
    List<Shoppingcart> movList = ShoppingcartTable.findListShoppingcartById(cartPK);
    Iterator<Shoppingcart> itr = movList.iterator();
%>
<body>
    <form name="addToCartForm" action="COCT" method="post">
        <center>
            <h1>Shopping Cart</h1>
            <table border="1">
                <tr>
                    <th>DVD Names</th>
                    <th>Rating</th>
                    <th>Year</th>
                    <th>Price/Unit</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
                <%
                    int totalPrice = 0;
                    while(itr.hasNext()) {
                        movCart = itr.next();
                        totalPrice += movCart.getQuantity() * movCart.getProducts().getPrice();
                %>
                <tr>
                    <td><%= movCart.getProducts().getMovie() %></td>
                    <td><%= movCart.getProducts().getRating() %></td>
                    <td><%= movCart.getProducts().getYearcreate() %></td>
                    <td><%= movCart.getProducts().getPrice() %></td>
                    <td><%= movCart.getQuantity() %></td>
                    <td><%= movCart.getQuantity() * movCart.getProducts().getPrice() %></td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan='5' align='center'>Total</td>
                    <td><%=totalPrice%></td>
                </tr>
            </table>
            <br>
            <button>Check out</button>
        </center>
    </form>
</body>
</html>
