window.onload = function () {
    initFieldDisablingBehaviour();
    initValueCheckingPolicy();
}

function initFieldDisablingBehaviour() {
    const hostsCountField = document.getElementById("hostsCount");
    const radios = document.querySelectorAll("input[type='radio']");

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

function initValueCheckingPolicy() {
    const ipPattern = new RegExp("\\d+\\.\\d+\\.\\d+\\.\\d+");
    const cDirPattern = new RegExp("/\\d+");
    const listPattern = new RegExp("(\\d+)(,\\s*\\d+)*");

    const ipField = document.getElementById("ipField");
    const maskField = document.getElementById("maskField");
    const hostsCountField = document.getElementById("hostsCount");

    
    const operate = (field, predicate) => {
        let validContent = predicate(field.value);
        if (!validContent) field.classList.add("error");
        else field.classList.remove("error");
    };

    const ipFieldListener = () => { operate(ipField, it => match(ipPattern, it)); };
    const maskFieldListener = () => { operate(maskField, it => (match(ipPattern, it) || match(cDirPattern, it))); };
    const hostsCountFieldListener = () => { operate(hostsCountField, it => match(listPattern, it)); };

    ipField.addEventListener('input', ipFieldListener);
    maskField.addEventListener('input', maskFieldListener);
    hostsCountField.addEventListener('input', hostsCountFieldListener);
}

function match(regex, value) {
    const parts = regex.exec(value);
    return parts != null && parts[0] == value;
}
