const ipPattern = new RegExp("\\d+\\.\\d+\\.\\d+\\.\\d+");
const cDirPattern = new RegExp("/\\d+");
const listPattern = new RegExp("(\\d+)(,\\s*\\d+)*");


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

    const ipField = document.getElementById("ipField");
    const maskField = document.getElementById("maskField");
    const hostsCountField = document.getElementById("hostsCount");

    
    const operate = (field, predicate) => {
        let validContent = predicate(field.value);
        if (!validContent) field.classList.add("error");
        else field.classList.remove("error");
    };

    const ipFieldListener = () => { operate(ipField, it => matchesIpPattern(it)); };
    const maskFieldListener = () => { operate(maskField, it => matchesMaskPattern(it)); };
    const hostsCountFieldListener = () => { operate(hostsCountField, it => match(listPattern, it)); };

    ipField.addEventListener('input', ipFieldListener);
    maskField.addEventListener('input', maskFieldListener);
    hostsCountField.addEventListener('input', hostsCountFieldListener);
}

function matchesIpPattern(ipAddress) {
    if (match(ipPattern, ipAddress)) {
        for (octet of ipAddress.split(".").map(it => Number.parseInt(it))) 
            if (Number.isNaN(octet) || octet < 0 || octet > 255)
                return false;
    
        return true;
    }

    return false;
}

function matchesMaskPattern(mask) {
    if (matchesIpPattern(mask)) {
        const octets = mask.split(",").map(it => Number.parseInt(it));
        for (i = 1; i < octets.length; i++) {
            let prev = octets[i -1];
            let current = octets[i];

            if (current > prev)
                return false;
        }

        return true;
    }
    if (match(cDirPattern, mask)) {
        const maskNum = Number.parseInt(mask.substring(1));
        return maskNum > 0 && maskNum < 33;
    }

    return false;
}


function match(regex, value) {
    const parts = regex.exec(value);
    return parts != null && parts[0] == value;
}
