<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<%@ page import=" org.slf4j.Logger, org.slf4j.LoggerFactory"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Riepilogo</title>

<%@ include file="/WEB-INF/views/fragments/head.jspf" %>

<link rel="stylesheet" href="<c:url value="/resources/assets/css/riepilogo.css" />"/>
<link rel="stylesheet" href="<c:url value="/webjars/mdbootstrap/4.20.0/css/mdb.min.css" />"/>
<script src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/webjars/mdbootstrap/4.20.0/js/mdb.min.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js"></script>

<script type="text/javascript" src="<c:url value="/resources/assets/js/warning.js" />"></script>
</head>
<body>
 <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
	
	 <section class="mt-4 w-100 pb-0">
	  <%@ include file="/WEB-INF/views/fragments/hero.jspf" %>
	  </section>
	  
	  <div id="topR" class="container-fluid my-0 p-3 h-100">
  <div class="row gy-3">
    <div class="col-12">
      <div class="d-flex w-100 justify-content-center">
        <h1 class="text-info">Riepilogo ordini</h1>
      </div>
    </div>
    <c:if test="${acquisti != null && acquisti.size() > 0}">
      <c:forEach items="${acquisti}" var="acquisto" varStatus="loop">
        <div class="col-3"></div>
      <div class="col-6">

          <div class="card w-100 h-100 p-2">
            <div class="a row no-gutters justify-content-center w-100 h-100" >
            <div class="col-5">
              <div class="card-header d-flex flex-column py-2">
                <i class="fa-solid fa-hashtag align-self-center my-2 fa-xl"></i>
                <h4 class="card-text">Prenotazione <br/>numero: ${acquisto.prenotazioneId}</h4>
              </div>
            </div>
            <div class="col-5">
              <div class="card-body d-flex flex-column py-2">
                <i class="fa-solid fa-hotel align-self-center my-2 fa-xl"></i>
                <h2 class="card-text">${alloggi.get(acquisto.prenotazioneId).nome}</h2>
                <h4 class="card-text">località ${cities.get(acquisto.prenotazioneId).nome}</h4>
                <h4 class="card-text">data ${acquisto.prenotazione.data}</h4>
                <h4 class="card-text">per ${acquisto.prenotazione.numeroGiorni} giorni</h4>
                <h4 class="card-text">prezzo: ${acquisto.prenotazione.prezzo} euro</h4>
              </div>
            </div>
            <div class="col-2">

              <div class="card-footer d-flex flex-column w-100 h-100 py-2">
                <i class="fa-solid fa-calendar-days align-self-center my-2 fa-xl"></i>
                <div class="d-flex flex-column justify-content-center align-items-center  w-100 h-100">
                  <h4 class="card-text fs-6">pagamento:</h4>
                  <h4 class="card-text">${acquisto.data}</h4>
                </div>
              </div>
            </div>
            </div>
        </div>
      </div>
      <div class="col-3"></div>


      </c:forEach>
    </c:if>

    
    <div class="col-12">
      <div class="d-flex flex-column align-items-end justify-content-center">
        <a class="btn btn-success" href="<%= request.getContextPath()+"/home"%>">Chiudi</a>
      </div>
    </div>
  </div>
</div>



	  
	  
	  
	  
	  <%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
</body>
</html>