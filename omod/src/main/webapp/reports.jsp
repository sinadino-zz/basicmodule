<%@ include file="/WEB-INF/template/include.jsp"%>
<!-- include file="/WEB-INF/template/header.jsp"   -->
<%@ include file="includesPharm/headerPharm.jsp" %>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Day', 'Total Views'],
          
          <c:forEach var="note" items="${stats}" varStatus="counter">
            ['${counter.count}', ${note}],
          </c:forEach>
 
        ]);

		
	var options = {
         
		  curveType: 'function',
    	  theme: 'maximized'	
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
		
	
		
      }
      
      
      $( document ).ready(function() {
            
            // initialize datepickers 
            $( "#start_date" ).datepicker();
            $( "#end_date" ).datepicker();
        });
    </script>

<div style=" width:960px; margin-left:auto; margin-right:auto;">
	
    <br>
	<div class="row">
    	
        <div class="col-sm-12">
        <ul class="nav nav-tabs">
          <li class="active"><a href="#">Patient Views</a></li>
          <li><a href="./reportPatientsAdded.form">Patient Additions</a></li>
          <li><a href="./userActions.form">User Actions</a></li>
          <li><a href="./patientVisits.form">Patient Visits</a></li>
          <li><a href="./patientEncounters.form">Patient Encounters</a></li>
        </ul>
        
        <div style=" text-align:right;">
        	<h3>Patient Views</h3>
        </div>
        
        <div style=" background-color:#efefef; padding:20px;">
            <form class="form-inline" action="" method="post">
        	<!--<div class="form-group">
        	<label>Patient</label> <input type="text" name="patient" value="" class="form-control" placeholder="Search by Name">
            </div>
            <div class="form-group">
            <label>User</label> <input type="text" name="user" value="" class="form-control" placeholder="Search by Name/ID">
            </div>-->
         
         
            <div class="form-group">
            <label>Select Month & Year</label> 
            <select class="form-control" name="statYear">
                <option value="2014">2014</option>
                
            </select>
            <select class="form-control" name="statMonth">
                <option value="1">January</option>
                <option value="2">February</option>
                <option value="3">March</option>
                <option value="4">April</option>
                <option value="5">May</option>
                <option value="6">June</option>
                <option value="7">July</option>
                <option value="8">August</option>
                <option value="9">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
                
            </select>
            <input type="submit" value="Search" class="btn btn-success">
            </div>

            
        </form>
        </div>
        
        <div id="chart_div" style="width:auto; height: 400px;"></div>
        </div>
    
    </div>
    <br/>
    <div class="row">
    	
        	<div class="col-sm-6">
        	
            <legend>Frequently Viewed Patients</legend>
                <table class="table table-bordered table-striped" style=" font-size: 12px;"> 
            	<tr class="info" style=" font-weight:bold;">
                	<td>Name</td>
                        <td>Total Views</td>
                        <td>Last Access Date</td>
                </tr>
                
                <c:forEach items="${vpplist}" var="patient">
                    <tr>
                	<td>${patient.patientName}</td>
                        <td>${patient.numberOfViews}</td>
                        <td>${patient.lastAccessDate}</td>
                    </tr>
                    
                </c:forEach>
                
                
            </table>
            
        </div>
        
        <div class="col-sm-6">
        	<legend>Recently Viewed Patients</legend>
            
                <table class="table table-bordered table-striped" style="font-size: 12px;"> 
            	<tr class="info" style=" font-weight:bold;">
                    <td>Name</td>
                    
                    <td>Last Access Date</td>
                </tr>
                
                <c:forEach items="${rvplist}" var="pat">
                    <tr>
                	<td>${pat.patientName}</td>
                        <td>${pat.lastAccessDate}</td>
                       
                </tr>
                    
                </c:forEach>
                
                
                
            </table>
            
        </div>
    </div>


</div>



<%@ include file="/WEB-INF/template/footer.jsp"%>
