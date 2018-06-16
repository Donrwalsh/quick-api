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
            contentType: "application/json"
        });
    })
});