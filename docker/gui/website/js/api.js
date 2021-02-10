import {getApiUrl} from '/js/config.js';

export function getObject(url, fun) {
    const request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            if(this.responseText) {
                fun(JSON.parse(this.responseText))
            }
        }
    };
    request.open("GET", getApiUrl() + url, true);
    request.send();
}

