const maxLeaves = 10; // Nombre maximum de feuilles visibles
let currentLeaves = 0; // Compteur de feuilles actives

function createFallingLeaf() {
    // Vérifiez si le nombre de feuilles actives est inférieur à la limite
    if (currentLeaves < maxLeaves) {
        const leaf = document.createElement("div");
        leaf.classList.add("falling-leaf");
        leaf.innerHTML = "🍃";
        leaf.style.left = `${Math.random() * 100}vw`;
        leaf.style.animationDuration = `${Math.random() * 3 + 2}s`;
        leaf.style.fontSize = `${Math.random() * 0.5 + 1.5}rem`;

        // Incrémentez le compteur de feuilles actives et ajoutez la feuille
        currentLeaves++;
        document.querySelector(".falling-leaves-container").appendChild(leaf);

        // Diminuez le compteur lorsque l'animation est terminée et supprimez la feuille
        leaf.addEventListener("animationend", () => {
            leaf.remove();
            currentLeaves--;
        });
    }
}

// Création de feuilles toutes les 2 secondes
setInterval(createFallingLeaf, 2000);
