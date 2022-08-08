const SP = "sp";
const SI = "si";

window.onload = function () {
    initFieldDisablingBehaviour();
}

function initFieldDisablingBehaviour() {
    let hostsCountField = document.getElementById("hostsCount");
    let radios = document.querySelectorAll("input[type='radio']");

    const listener = function() {
        console.log("Radio clicked");
        let partititon = document.getElementById("partitioningBox").checked;
        hostsCountField.disabled = !partititon;
        console.log(hostsCountField.disabled);
    }

    radios.forEach(it => {
        it.addEventListener('change', listener);
    });
    
    listener();
}