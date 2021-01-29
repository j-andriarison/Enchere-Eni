$(document).ready(function() {
	console.log("ready!"); 
	jQuery.validator.setDefaults({
		debug: true,
		success: "valid"
	});

	$('#inscription').validate({
		rules: {
			pseudo:
			{
				required: true,
				minlength: 3,
				maxlength: 20
			},
			email:
			{
				required: true,
				email: true
			},
			nom:{
				required: true,
				minlength: 3,
				maxlength: 50
			},
			prenom:{
				required: true,
				minlength: 3,
				maxlength: 50
			},
			telephone:{
				required: true,
				minlength: 10,
				maxlength: 20
			},
			motDePasse:
			{
				required: true,
				minlength: 3,
				maxlength: 10
			},
			verif_password: {
				equalTo: "#motDePasse"

			},
			rue:{
				required: true,
				minlength: 10,
				maxlength: 50
			},
			codePostal:{
				required: true,
				number: true,
				digits: true,
				minlength: 5,maxlength: 5
			},
			ville:{
				required: true,
				minlength: 5,
				maxlength: 50
			}
		},

		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error');

		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement: 'span',
		errorClass: 'alert-danger',
		errorPlacement: function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		},
        submitHandler: function(form) {
            form.submit(); // <- use 'form' argument here.
        }

	})
});