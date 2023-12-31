 
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Login</title>

<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/fragments/head.jspf" %>
<spring:url value="/resources/css/login.css" var="crunchifyCSS" />
<link rel="stylesheet" href="<c:url value="/resources/assets/css/login.css" />"/>
<script type="text/javascript" src="<c:url value="/resources/assets/js/login.js" />"></script>
</head>
<body>

<jsp:useBean id="obj" class="capstone.fe.spring.model.LoginRequest"/>  
  
<jsp:setProperty property="*" name="obj"/>  

<div id="main" class="d-flex justify-content-center align-items-center">
  <div>
    <form action="accedi(form)">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="username" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button id="btn" type="submit" class="btn btn-primary mt-3" disabled=""><i class="fa-solid fa-right-to-bracket me-2"></i>Accedi</button>
    </form>
    <div class="my-2 w-100 d-flex justify-content-center">
      <a  [routerLink]="['/register']" class="active">Registrati</a>
    </div>
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
                    <p class="heading">Prego!</p>
                  </div>

                  <!--Body-->
                  <div class="modal-body">

                    <i class="fas fa-solid fa-thumbs-up fa-4x animated rotateIn mb-4"></i>

                    <p>Autenticazione avvenuta con successo.</p>

                  </div>

                  <!--Footer-->
                  <div class="modal-footer flex-center">

                    <a type="button" class="btn btn-outline-info waves-effect"  data-mdb-dismiss="modal" >OK</a>
                  </div>
                </div>
                <!--/.Content-->
              </div>
              </div>
              <!--Modal: modalPush-->



</body>
</html>