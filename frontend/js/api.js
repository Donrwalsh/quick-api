$(function () {
    "use strict";

    $('.create').submit(function() {
        event.preventDefault(); //otherwise page reloads
        var formData = {
            'first_name' : $('input[name=first_name]').val(),
            'last_name' : $('input[name=last_name]').val(),
            'gender' : $('input[name=gender]').val(),
            'birth_date' : $('input[name=birth_date]').val(),
            'hire_date' : $('input[name=hire_date]').val()
        };

        $.ajax({
            type: "POST",
            url: "/employees/employees/",
            data: JSON.stringify(formData),
            processData: false,
            contentType: "application/json",
            success: function(res, status, xhr) {
                if (xhr.status === 201) {
                    $.each(res, function (i, response) {
                       let resJSON = "";
                       if (response === "") {
                           resJSON = "{Empty Response}";
                       } else {
                           resJSON += "<p>";
                           resJSON += "<strong>Status:</strong> " + xhr.status + "<br />";
                           resJSON += "<strong>ID:</strong> " + response.empNo + "<br />";
                           resJSON += "<strong>First Name:</strong> " + response.firstName + "<br />";
                           resJSON += "<strong>Last Name:</strong> " + response.lastName + "<br />";
                           resJSON += "<strong>Gender:</strong> " + response.gender + "<br />";
                           resJSON += "<strong>Birth Date:</strong> " + response.birthDate + "<br />";
                           resJSON += "<strong>Hire Date:</strong> " + response.hireDate + "<br />";
                           resJSON += "</p>"
                       }
                       $(".output").empty().append(resJSON);
                    });
                } else {
                    alert("failure");
                }
            }
        });
    })
});