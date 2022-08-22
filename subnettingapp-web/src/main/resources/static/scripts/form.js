const ipPattern = new RegExp("\\d+\\.\\d+\\.\\d+\\.\\d+");
const cDirPattern = new RegExp("/\\d+");
const listPattern = new RegExp("(\\d+)(,\\s*\\d+)*");

const masksForIpClasses = {
    "A": 8,
    "B": 16,
    "C": 24
};

window.onload = function () {
    initFieldDisablingBehaviour();
    initValueCheckingPolicy();
}

function initFieldDisablingBehaviour() {
    const hostsCountField = document.getElementById("hostsCount");
    const radios = document.querySelectorAll("input[type='radio']");

    const listener = function () {
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
        return validContent;
    };

    const ipFieldListener = () => {
        if (operate(ipField, it => matchesIpPattern(it))) {
            if (!document.cookie.includes("auto_mask=false")) {
                maskField.value = formatMask(
                    calculateAutoMask(ipField.value),
                    !document.cookie.includes("auto_mask_format=ddn")
                );
            }
        }
    };
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
            let prev = octets[i - 1];
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


function formatMask(maskBits, cdir) {
    if (maskBits == 0) 
        return "";
    if (cdir) {
        return `/${maskBits}`;
    } else {
        let formatted = "";
        let bitCount = maskBits;

        for (i = 0; i < 4; i++) {

            let octet = 0;

            for (j = 7; j >= 0; j--) {
                if (bitCount == 0) break;
                octet |= (1 << j);
                bitCount -= 1;
            }

            formatted += `${octet}.`;
        }

        return formatted.substring(0, formatted.length - 1);
    }
}

function calculateAutoMask(ipAddress) {
    let ipClass = getClassForIp(ipAddress);
    let maskCalc = masksForIpClasses[ipClass];
    return maskCalc == undefined ? "" : maskCalc;
}

function getClassForIp(ipAddress) {
    let firstOctet = Number.parseInt(ipAddress.substring(0, ipAddress.indexOf(".")));
    return firstOctet < 128 ? "A"
        : firstOctet < 192 ? "B"
            : firstOctet < 224 ? "C"
                : firstOctet < 240 ? "D"
                    : firstOctet < 255 ? "E"
                        : "-";
}

function match(regex, value) {
    const parts = regex.exec(value);
    return parts != null && parts[0] == value;
}
