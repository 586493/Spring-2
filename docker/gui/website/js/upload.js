import {addDclListener} from '/js/load.js';
import {getApiUrl} from '/js/config.js';

const form = document.getElementById("form");
const authorInput = document.getElementById("author");
const descriptionInput = document.getElementById("description");
const fileInput = document.getElementById("file");
const fileNameInput = document.getElementById("fileName");
const fileInputDiv = document.getElementById("fileInputDiv");

addDclListener(() => {
    fileInput.addEventListener('change',
        () => {
            if(fileInput.files[0]) {
                fileNameInput.value = fileInput.files[0].name;
            } else {
                fileNameInput.value = "";
            }
        });
    fileInputDiv.addEventListener("click",
        () => {
            fileInput.click();
        });
    fileNameInput.addEventListener("keyup",
        () => {
            fileInput.click();
        });
    form.addEventListener('submit', event => upload(event));
});

export function upload(event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
    formData.append("author", authorInput.value);
    formData.append("description", descriptionInput.value);

    const request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4) {
            if(this.status > 201) {
                if(this.responseText) {
                    alert(this.responseText);
                } else {
                    alert("status: " + this.status);
                }
            } else {
                window.location.replace('/subpages/list.html');
            }
        }
    };
    request.open("POST", getApiUrl() + 'uploads/', true);
    request.send(formData);
}
