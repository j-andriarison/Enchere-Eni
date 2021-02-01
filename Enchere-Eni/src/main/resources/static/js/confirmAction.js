
	$('#confirmDelete').confirm({
    content: "Votre compte sera supprimer definitivement! <br><span class='text-danger'>Voulez-vous continuer?</span>",

    buttons: {
        Oui: function(){
            location.href = this.$target.attr('target-action');
        },
        Non: function(){
        }
    }
});