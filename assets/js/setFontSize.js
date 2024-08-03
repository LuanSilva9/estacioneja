function expandsFont(element) {
    const getElement = document.querySelectorAll(element || null);

    getElement.forEach((p) => {
        let currentFontSize = window.getComputedStyle(p).fontSize;
        let newFontSize = parseFloat(currentFontSize) + 2;

        let increment = 2;

        let maxFontSize = 24;

        if (newFontSize < maxFontSize) {
            newFontSize += increment;

            if (newFontSize > maxFontSize) {
                newFontSize = maxFontSize;
            }
            p.style.fontSize = newFontSize + "px";
        }

    })
}

function reducesFont(element) {
    const getElement = document.querySelectorAll(element);

    getElement.forEach((p) => {
        let currentFontSize = window.getComputedStyle(p).fontSize;
        let newFontSize = parseFloat(currentFontSize);

        let decrement = 2;
        let minFontSize = 16;

        if (newFontSize > minFontSize) {
            newFontSize -= decrement;

            if (newFontSize < minFontSize) {
                newFontSize = minFontSize;
            }
            p.style.fontSize = newFontSize + "px";
        }
    });
}