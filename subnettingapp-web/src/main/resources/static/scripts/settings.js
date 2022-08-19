window.onload = function() {
    let saveButton = document.querySelector("input[type='submit']");
    saveButton.disabled = true;

    document.querySelectorAll("select").forEach((it) => {
        it.addEventListener("change", () => {
            saveButton.disabled = false;
        });
    });
}