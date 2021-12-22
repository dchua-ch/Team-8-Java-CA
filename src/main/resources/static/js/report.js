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
			}
		});
	});
});

