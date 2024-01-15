 
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Register</title>

<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/fragments/head.jspf" %>
<spring:url value="/resources/css/login.css" var="crunchifyCSS" />
<link rel="stylesheet" href="<c:url value="/resources/assets/css/register.css" />"/>
<script src="/webjars/bootstrap/dist/js/bootstrap.esm.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js"></script>
<script type="module" src="<c:url value="/resources/assets/js/register.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/assets/js/warning.js" />"></script>


</head>
<body>



<section class="container" ng-app="registerApp">
<div id="main" class="d-flex justify-content-center align-items-center" ng-controller="registerCtrl" ng-init="initRegister()">
<%@ include file="/WEB-INF/views/fragments/warning.jspf" %>
  <div>
    <form >
        <div class="form-group">
          <label for="name">Nome</label>
          <input name="name" required class="form-control" id="name" type="text">
            <label for="name">Cognome</label>
            <input name="surname" required class="form-control" id="surname" type="text">
           
          </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" name="email" id="email" class="form-control" required >
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required >
        </div>

        <button id="btn" type="submit" class="btn btn-primary mt-3" ng-click="register()" disabled=""><i class="fa-solid fa-id-card me-2"></i>Registrati</button>
      
    </form>
    <div class="my-2 w-100 d-flex justify-content-center">
      <a href="rl" class="active text-info">Accedi</a>
    </div>
  </div>


    <!--Modal: modalPush-->
    <div class="modal fade" id="modalSuccess" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-notify modal-info" role="document">
      <!--Content-->
      <div class="modal-content text-center">
        <!--Header-->
        <div class="modal-header d-flex justify-content-center">
          <p class="heading">Complimenti!</p>
        </div>

        <!--Body-->
        <div class="modal-body">

          <i class="fas fa-solid fa-thumbs-up fa-4x animated rotateIn mb-4"></i>

          <p>Registrazione avvenuta con successo.</p>

        </div>

        <!--Footer-->
        <div class="modal-footer flex-center">

          <a type="button" class="btn btn-outline-info waves-effect"  data-bs-dismiss="modal" ng-click="finish()">OK</a>
        </div>
      </div>
      <!--/.Content-->
    </div>
    </div>
    <!--Modal: modalPush-->
</div>
</section>


            



</body>
</html>