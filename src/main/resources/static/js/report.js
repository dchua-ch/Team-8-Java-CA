$(document).ready(function() {
	$("#btn").click(function(e) {
		e.preventDefault();
		$.ajax({
			method: 'GET',
			url: "http://localhost:8080/reportAPI/date",
			data: {
				id: $("#employee").val(),
				start: $("#startDate").val(),
				end: $("#endDate").val(),
				comp: $("#comp").is(":checked")
			},
			success: function(response) {
				console.log(response);
				buildTable(response)
			}
		});
	});
	$("#refresh").click(function() {
		location.reload();
	});
	$("#export").click(function() {
		$.ajax({
			method: 'GET',
			url: "http://localhost:8080/reportAPI/date",
			data: {
				id: $("#employee").val(),
				start: $("#startDate").val(),
				end: $("#endDate").val(),
				comp: $("#comp").is(":checked")
			},
			success: function(response) {
				csv = objectToCsv(response);
				downloadCsv(csv);
			}
		});
	})

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

function objectToCsv(data) {
	const csvRows = [];

	const headers = Object.keys(data[0]);
	csvRows.push(headers.join(','));

	for (const row of data) {
		const values = headers.map(header => {
			const escaped = ('' + row[header]).replace(/"/g, '\\"');
			return `"${escaped}"`;
		});
		csvRows.push(values.join(','));
	}
	return csvRows.join('\n');
}

function downloadCsv(data) {
	const blob = new Blob([data], {type: 'text/csv'});
	const url = window.URL.createObjectURL(blob);
	const a = document.createElement('a'); 
	a.setAttribute('hidden', '');
	a.setAttribute('href', url);
	a.setAttribute('download', 'download.csv');
	document.body.appendChild(a);
	a.click();
	document.body.removeChild(a);
}

