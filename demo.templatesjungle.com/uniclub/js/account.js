$(document).ready(function () {
  $(".btn-login").click(function () {
    let email = $("#email-login").val().toLowerCase();
    let password = $("#password-login").val().toLowerCase();

    $.ajax({
      url: "http://localhost:8080/uniclub/authen",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({
        email: email,
        password: password,
      }),
    }).done(function (data) {
      console.log(data);
    });
  });
});
