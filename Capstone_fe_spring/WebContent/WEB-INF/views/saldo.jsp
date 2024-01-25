<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<%@ page import=" org.slf4j.Logger, org.slf4j.LoggerFactory"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Saldo</title>

<%@ include file="/WEB-INF/views/fragments/head.jspf" %>

<link rel="stylesheet" href="<c:url value="/resources/assets/css/saldo.css" />"/>
<link rel="stylesheet" href="<c:url value="/webjars/mdbootstrap/4.20.0/css/mdb.min.css" />"/>
<script src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/webjars/mdbootstrap/4.20.0/js/mdb.min.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js"></script>
<script type="module" src="<c:url value="/resources/assets/js/saldo.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/assets/js/warning.js" />"></script>
<!--  
<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
-->


</head>
<body>
<script>
var baseUrl = '${baseUrl}';
var json ='${json}';
var token = '${token}';
var redirection = '<%=request.getContextPath() + "/riepilogo"%>';
</script>

 <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
	
	 <section class="mt-4 w-100 pb-0">
	  <%@ include file="/WEB-INF/views/fragments/hero.jspf" %>
	  </section>
	  <section class="container-fluid mx-0 p-0" ng-app="saldoApp" >

<div id="main" class="d-flex justify-content-center align-items-center flex-column w-100" ng-controller="saldoCtrl" ng-init="initSaldo()">
	  
	  <div id="topSaldo" class="container-fluid  w-100 h-100 p-4 my-0">
    <div class="row h-100 gy-2">
      <div class="col-12">
        <div class="d-flex w-100 justify-content-center">
          <h1>Saldo prenotazioni</h1>
        </div>
      </div>
      <c:if test="${prenotazioni != null && prenotazioni.size() > 0}">
        <c:forEach items="${prenotazioni}" var="prenotazione" varStatus="loop">
          <div class="col-2"></div>
        <div class="col-8">

            <div class="card w-100 h-100 py-2">
              <div class="a row no-gutters justify-content-center w-100 h-100" >
              <div class="col-4">
                <div class="card-header d-flex flex-column py-2">
                  <i class="fa-solid fa-hotel align-self-center my-2 fa-xl"></i>
                  <h2 class="card-text">${alloggi.get(prenotazione.id).nome}</h2>
                  <h4 class="card-text">località: ${cities.get(prenotazione.id).nome}</h4>
                  <h4 class="card-text"><c:out value="per ${prenotazione.numeroGiorni} giorni"/></h4>
                  <h4 class="card-text"><c:out value="prezzo: ${prenotazione.prezzo} euro"/></h4>
                  <h4 class="card-text"><c:out value="posti: ${prenotazione.numeroPosti}"/></h4>
                </div>
              </div>
              <div class="col-3">
                <div class="card-body d-flex flex-column py-2">
               
                  <i class="fa-solid fa-plane-circle-check align-self-center my-2 fa-xl"></i>
  
     
                <h4 class="card-text">Andata:<br/> <span class="fs-6">${trasporti.get(prenotazione.trasportoId).nome},</span ><br/> <span class="fs-6">${trasporti.get(prenotazione.trasportoId).dataPartenza}</span></h4>

                <h4 class="card-text">Ritorno: <br/> <span class="fs-6">${trasporti.get(prenotazione.ritornoId).nome}, </span><br/> <span class="fs-6">${trasporti.get(prenotazione.ritornoId).dataPartenza}</span></h4>
              </div>
              </div>
              <div class="col-4 h-100">
                <div class="card-footer d-flex flex-column h-100 py-2">
                  <i class="fa-solid fa-sack-dollar align-self-center my-2 fa-xl"></i>
                <div class="d-flex justify-content-center align-items-center  w-100 h-100" >
                  <button type="button" class="btn btn-success rounded" data-bs-toggle="modal" data-bs-target="#modalPushSaldo${prenotazione.id}">Acquista</button>
                </div>
                </div>
              </div>
              </div>
          </div>
        </div>
        <div class="col-2"></div>

        <div class="container">
          <!-- Modal -->
         <div class="modal" id="exampleModal${prenotazione.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
             <div class="modal-dialog">
               <div class="modal-content">
                 <div class="modal-header">
                   <h5 class="modal-title" id="exampleModalLabel">Attenzione!</h5>
                   <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                 </div>
                 <div class="modal-body">
                  <p>Sicuro di voler procedere con l'acquisto?</p>
                 </div>
                 <div class="modal-footer">
                   <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                   <button id="confirm" type="button" class="btn btn-outline-danger" data-bs-dismiss="modal" (click)="selezionaPrenotazione(prenotazione)">Conferma</button>
                 </div>
               </div>
             </div>
           </div>
               </div>


                 <!--Modal: modalPush-->
<div class="modal fade" id="modalPushSaldo${prenotazione.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
aria-hidden="true">
<div class="modal-dialog modal-notify modal-success" role="document">
  <!--Content-->
  <div class="modal-content text-center">
    <!--Header-->
    <div class="modal-header d-flex justify-content-center">
      <p class="heading">Attenzione!</p>
    </div>

    <!--Body-->
    <div class="modal-body">

      <i class="fas fa-solid fa-circle-dollar-to-slot fa-4x animated rotateIn mb-4"></i>

      <p>Sicuro di voler procedere con l'acquisto?</p>

    </div>

    <!--Footer-->
    <div class="modal-footer flex-center">
      <a  class="btn btn-success" ng-click="selezionaPrenotazione(${loop.index})" data-bs-dismiss="modal">Conferma</a>
      <a type="button" class="btn btn-outline-success waves-effect" data-bs-dismiss="modal">Annulla</a>
    </div>
  </div>
  <!--/.Content-->
</div>
</div>
<!--Modal: modalPush-->
        </c:forEach>
      </c:if>

      
    </div>
</div>

  </div>
	  
	  </section>
	  
	  
	  
	   <%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
	
</body>
</html>