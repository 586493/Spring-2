export function getParameter(name) {
    return new URLSearchParams(window.location.search).get(name);
}
