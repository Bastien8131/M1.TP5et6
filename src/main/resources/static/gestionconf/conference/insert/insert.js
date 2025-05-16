// Fonction pour auto-compléter le formulaire avec des données d'exemple
function autoCompleteForm() {
    document.getElementById("titreCongres").value = "Conférence Internationale 2023";
    document.getElementById("numEditionCongres").value = "5";
    document.getElementById("dtDebutCongres").value = "2023-12-01";
    document.getElementById("dtFinCongres").value = "2023-12-05";
    document.getElementById("urlSiteWebCongres").value = "https://www.conference-exemple.com";

    // Cocher quelques activités (maintenant avec IDs)
    document.getElementById("activite1").checked = true; // ID = 1
    document.getElementById("activite2").checked = true; // ID = 2

    // Cocher quelques thématiques (maintenant avec IDs)
    document.getElementById("theme1").checked = true; // ID = 0
    document.getElementById("theme4").checked = true; // ID = 3
    document.getElementById("theme6").checked = true; // ID = 5
}

// Appeler la fonction pour tester
autoCompleteForm();
