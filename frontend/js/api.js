$(function () {
    "use strict";

    $('.form').submit(function() {
        event.preventDefault();

        if ($(this).hasClass("read")) {
            var type = "GET";
            var url = "/employees/employees/" + $('input[name=R_emp_no]').val();
            var want_status = 200;
            var data = {};
            var contentType = {};

        } else if ($(this).hasClass("delete")) {
            var type = "DELETE";
            var url = "/employees/employees/" + $('input[name=D_emp_no]').val();
            var want_status = 204;
            var data = {};
            var contentType = {};

        } else if ($(this).hasClass("update")) {
            var type = "PUT";
            var url = "/employees/employees/" + $('input[name=U_emp_no]').val();
            var want_status = 200;
            var data = JSON.stringify({
                'first_name' : $('input[name=first_name]').val(),
                'last_name' : $('input[name=last_name]').val(),
                'gender' : $('input[name=gender]').val(),
                'birth_date' : $('input[name=birth_date]').val(),
                'hire_date' : $('input[name=hire_date]').val()
            });
            var contentType = "application/json";

        } else if ($(this).hasClass("create")) {
            var type = "POST";
            var url = "/employees/employees";
            var want_status = 201;
            var data = JSON.stringify({
                'first_name' : $('input[name=first_name]').val(),
                'last_name' : $('input[name=last_name]').val(),
                'gender' : $('input[name=gender]').val(),
                'birth_date' : $('input[name=birth_date]').val(),
                'hire_date' : $('input[name=hire_date]').val()
            });
            var contentType = "application/json";
        }

        $.ajax({
            type: type,
            url: url,
            data: data,
            processData: false,
            contentType: contentType,
            success: function(res, status, xhr) {
                if (xhr.status === want_status) {
                    $.each(res, function (i, response) {
                        var resJSON = "";
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
                        $(".output_content").empty().append(resJSON);
                    });
                } else {
                    alert("failure");
                }
            }
        })
    });
});