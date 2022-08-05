const SP = "sp";
const SI = "si";

window.onload = function () {

    let listener = (value) => {
        console.log("radio check detected: " + value);
        switch(radioButtons.value) {
            case SP:
                document.getElementById("hostsCount").removeAttribute("disabled");
                break;
            case SI:
                document.getElementById("hostsCount").setAttribute("disabled", "");
                break;
        } 
    };

    document.getElementById("partitioningBox").addEventListener("change", () => { listener(SP); });
    document.getElementById("simpleInfoBox").addEventListener("change", () => { listener(SI); });

   // listener(document.getElementById("partitioningBox").getAttribute("checked") != null ? SP : SI);
}
