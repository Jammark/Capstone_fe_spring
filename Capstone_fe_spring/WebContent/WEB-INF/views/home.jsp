
 
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>

    <link
  href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"
  rel="stylesheet"
  integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
  crossorigin="anonymous"
/>
<script
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
  integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
  crossorigin="anonymous"
></script>
<!-- Font Awesome -->
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
  rel="stylesheet"
/>
<!-- Google Fonts -->
<link
  href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
  rel="stylesheet"
/>
<!-- MDB -->
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.css"
  rel="stylesheet"
/>

<script
  type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.js"
></script>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</p>

	<form action="user" method="post">
		<input type="text" name="userName"><br> <input
			type="submit" value="Login">
			<input type="button" data-mdb-toggle="modal" data-mdb-target="#modalPushPrenota">test</input>
	</form>
	<!-- MDB -->
	
	       <!--Modal: modalPush-->
<div class="modal fade" id="modalPushPrenota" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
aria-hidden="true">
<div class="modal-dialog modal-notify modal-info" role="document">
  <!--Content-->
  <div class="modal-content text-center">
    <!--Header-->
    <div class="modal-header d-flex justify-content-center">
      <p class="heading">Attenzione!</p>
    </div>

    <!--Body-->
    <div class="modal-body">

      <i class="fas fa-bell fa-4x animated rotateIn mb-4"></i>

      <p>Sicuro di voler procedere con la prenotazione?</p>

    </div>
    
    <!--Footer-->
    <div class="modal-footer flex-center">
      <a  class="btn btn-info" data-mdb-dismiss="modal">Conferma</a>
      <a type="button" class="btn btn-outline-info waves-effect" data-mdb-dismiss="modal">Annulla</a>
    </div>
  </div>
  <!--/.Content-->
</div>
</div>

</body>
</html>