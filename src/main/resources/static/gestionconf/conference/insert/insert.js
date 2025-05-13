// Fonction pour auto-compléter le formulaire avec des données d'exemple
function autoCompleteForm() {
    document.getElementById("titreCongres").value = "Conférence Internationale 2023";
    document.getElementById("numEditionCongres").value = "5";
    document.getElementById("dtDebutCongres").value = "2023-12-01";
    document.getElementById("dtFinCongres").value = "2023-12-05";
    document.getElementById("urlSiteWebCongres").value = "https://www.conference-exemple.com";

    // Cocher quelques activités
    document.getElementById("activite1").checked = true;
    document.getElementById("activite2").checked = true;

    // Cocher quelques thématiques
    document.getElementById("theme1").checked = true;
    document.getElementById("theme4").checked = true;
    document.getElementById("theme6").checked = true;
}

// Appeler la fonction pour tester
autoCompleteForm();