/**
 * window.addEventListener('DOMContentLoaded', listener);
 * @param listener
 */
export function addDclListener(listener) {
    window.addEventListener('DOMContentLoaded', listener);
}

export function injectComponent(url, id, callAfter = null) {
    let request = new XMLHttpRequest();
    request.onload = function() {
        if (this.readyState === 4 && this.status === 200) {
            if(request.responseText) {
                document.getElementById(id).innerHTML = request.responseText;
            }
            if(callAfter) {
                callAfter();
            }
        }
    };
    request.open('GET', url, true);
    request.send();
}

addDclListener(() => injectComponent('/components/nav.html', 'nav'));
