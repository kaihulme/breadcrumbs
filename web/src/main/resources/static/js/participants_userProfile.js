// Jansy-Bootstrap add link to whole table row
$('tbody.rowlink').rowlink()

// navbar button functionality to toggle sidebar
$('#sidebarCollapse').on('click', function () {
    $('#sidebar, #content').toggleClass('active');
});
