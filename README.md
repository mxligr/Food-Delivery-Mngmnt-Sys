# Food-Delivery-Mngmnt-Sys
A food delivery management application for a catering company, developed for the Fundamental Programming Techniques course // 2nd year, 2nd semester @ Computer Science, TUCN

In the application implemented in Java, the client can order products from the company's menu. <br>
The system has three types of users that log in using a username and a password: administrator, regular employee, and client. <br>

The administrator can: <br>
• Import the initial set of products which will populate the menu from a .csv file. <br>
• Manage the products from the menu: add/delete/modify products and create new products composed of several products (an example of composed product could be named “daily menu 1” composed of a soup, a steak, a garnish, and a dessert). <br>
• Generate reports about the performed orders considering the following criteria: <br>
  o time interval of the orders – a report should be generated with the orders performed between a given start hour and a given end hour regardless the date. <br>
  o the products ordered more than a specified number of times so far. <br>
  o the clients that have ordered more than a specified number of times and the value of the order was higher than a specified amount. <br>
  o the products ordered within a specified day with the number of times they have been ordered. <br>
  
The client can:<br>
• Register and use the registered username and password to log in within the system. <br>
• View the list of products from the menu. <br>
• Search for products based on one or multiple criteria such as keyword (e.g. “soup”), rating, number of calories/proteins/fats/sodium/price. <br>
• Create an order consisting of several products – for each order the date and time will be persisted and a bill will be generated that will list the ordered products and the total price of the order. <br>

The employee is notified each time a new order is performed (using the Observer Design Pattern) by a client so that it can prepare the delivery of the ordered dishes.<br>

The data from the .csv is read using streams.<br>
Lambda expressions and stream processing is used for generating the administrator specific reports and for search functionalities. <br>
The Composite Design Pattern was used for modelling classes MenuItem, BaseProduct and CompositePorduct. <br>
A bill is generated in a .txt format. <br>
JavaDoc files can be generated for documenting the functionalities of the application. <br>
Serialization is used to ensure the data necessary for the application is up to date. <br>


