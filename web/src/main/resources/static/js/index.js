// navbar button functionality to toggle sidebar
$('#sidebarCollapse').on('click', function () {
    $('#sidebar, #content').toggleClass('active');
});

$('#loginBtn').on('click', function () {
    $('#loginBtn').button('dispose');
    $('#spinner').toggleClass('active');
});

// Jansy-Bootstrap add link to whole table row
$('tbody.rowlink').rowlink()

// slider counter
$("#x_coord").slider();
$("#x_coord").on("slide", function(slideEvt) {
	$("#x_coordSliderVal").text(slideEvt.value);
});
$("#y_coord").slider();
$("#y_coord").on("slide", function(slideEvt) {
	$("#y_coordSliderVal").text(slideEvt.value);
});

//function equalHeight(group) {
//	var tallest = 0;
//	group.each(function() {
//		var thisHeight = $(this).height();
//		if(thisHeight > tallest) {
//			tallest = thisHeight;
//		}
//	});
//	group.height(tallest);
//}
//
//$(document).ready(function() {

//	equalHeight($(".col-question"));
//});

// search table and filter results
function searchUserTable(event) {
    var filter = event.target.value.toUpperCase();
    var rows = document.querySelector("#users").rows;

    for (var i = 1; i < rows.length; i++) {
        var firstCol = rows[i].cells[0].textContent.toUpperCase();
        var secondCol = rows[i].cells[1].textContent.toUpperCase();
        var thirdCol = rows[i].cells[2].textContent.toUpperCase();
        if (firstCol.indexOf(filter) > -1 || secondCol.indexOf(filter) > -1 || thirdCol.indexOf(filter) > -1) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
} document.querySelector('#search').addEventListener('keyup', searchUserTable, false);

function searchExpertTable(event) {
    var filter = event.target.value.toUpperCase();
    var rows = document.querySelector("#experts").rows;

    for (var i = 1; i < rows.length; i++) {
        var firstCol = rows[i].cells[0].textContent.toUpperCase();
        var secondCol = rows[i].cells[1].textContent.toUpperCase();
        var thirdCol = rows[i].cells[2].textContent.toUpperCase();
        if (firstCol.indexOf(filter) > -1 || secondCol.indexOf(filter) > -1 || thirdCol.indexOf(filter) > -1) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
} document.querySelector('#searchExperts').addEventListener('keyup', searchExpertTable, false);

// sort table
function sortTable(id, n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById(id);
  switching = true;
  dir = "asc";
  while (switching) {
    switching = false;
    rows = table.rows;
    for (i = 1; i < (rows.length - 1); i++) {
      shouldSwitch = false;
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      switchcount ++;
    } else {
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}
