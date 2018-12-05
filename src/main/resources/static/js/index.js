$('#loginButton').on('click', function () {
    logIn();
});

$("#password").on('keyup', function (e) {
    if (e.keyCode == 13) {
        logIn();
    }
});

function logIn () {
  window.location.href = "views/participants.html";
}
