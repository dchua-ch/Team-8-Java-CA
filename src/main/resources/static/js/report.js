$(document).ready(function() {
	$("#btn").click(function(e) {
		e.preventDefault();
		$.ajax({
			method: 'GET',
			url: "http://localhost:8080/reportAPI/date",
			data: {
				id: $("#employee").val(),
				start: $("#startDate").val(),
				end: $("#endDate").val()
			},
			success: function(response) {
				console.log(response); 
				buildTable(response)
			}
		});
	});
});

function buildTable(data) {
	var table = document.getElementById("myTable")

	for (var i = 0; i < data.length; i++) {
		var row = `<tr>
							<td>${data[i].leaveId}</td>
							<td>${data[i].name}</td>
							<td>${data[i].startDate}</td>
							<td>${data[i].endDate}</td>
							<td>${data[i].leaveType}</td>
					  </tr>`
			table.innerHTML += row
	}
}


/*$(document).ready(function() {
	$("#btn").click(function(e) {
		e.preventDefault();
		$.ajax({
			method: 'GET',
			url: "http://localhost:8080/reportAPI/date",
			data: {
				id: $("#employee").val(),
				start: $("#startDate").val(),
				end: $("#endDate").val()
			},
			success: function(response) {
				leaves = response.data
				buildTable(leaves)
			}
		});
	});
});

var leaves = []

function buildTable(data) {
	var table = document.getElementById("myTable")

	for (var i = 0; i < data.length; i++) {
		var row = <tr>
			<td>${data[i].leaveId}</td>
			<td>${data[i].startDate}</td>
			<td>${data[i].endDate}</td>
			<td>${data[i].leaveType}</td>
		</tr>
		table.innerHTML += row
	}
}



//try2 
$.getJSON("http://localhost:8080/reportAPI/date", { id: $("#employee").val(), start: $("#startDate").val(), end: $("#endDate").val() })
	.done(function(json) {
		buildTable(json);
	});
*/

/*$(document).ready(function() {
	$("#btn").click(function(e) {
		e.preventDefault();
		$.getJSON("http://localhost:8080/reportAPI/date", { id: $("#employee").val(), start: $("#startDate").val(), end: $("#endDate").val() })
			.done(function(json) {
				buildTable(json);
			});
	});
});

function buildTable(data) {
	var table = document.getElementById("myTable")

	for (var i = 0; i < data.length; i++) {
		var row = <tr>
			<td>${data[i].leaveId}</td>
			<td>${data[i].startDate}</td>
			<td>${data[i].endDate}</td>
			<td>${data[i].leaveType}</td>
		</tr>
		table.innerHTML += row
	}
}*/

