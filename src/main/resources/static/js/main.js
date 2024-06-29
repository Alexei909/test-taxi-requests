'use strict';

const userPage = document.querySelector("#user-page")
const userForm = document.querySelector("#userForm");
const buttonPage = document.querySelector("#button-page");
const buttonOrder = document.querySelector("#button-order");
const orderPage = document.querySelector("#chat-page");
const messagePage = document.querySelector("#chat-messages");
const wait = document.querySelector("#button-label");
const onTheWay = document.querySelector("#taxi-page");
const endOrder = document.querySelector("#button-end-order");

let stompClient = null;
let username = null;
let email = null;
let clientNamePage = null;

function connect(event) {
    username = document.querySelector("#name").value.trim();
    email = document.querySelector("#email").value.trim();

    if (username && email) {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    userPage.classList.add("hidden");
    if (username.startsWith("taxi-")) {
        orderPage.classList.remove("hidden");
        stompClient.subscribe("/topic/taxiDrivers", onTaxiMessageReceived);
    } else {
        buttonPage.classList.remove("hidden");
        stompClient.subscribe(`/user/${username}/queue/message`, onUserMessageReceived);

    }

    stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({name: username, email: email})
    );
}

function makeOrder(event) {
    if (stompClient && username) {
        stompClient.send("/app/user.makeOrder",
            {},
            JSON.stringify({clientName: username})
        );
    }
    buttonOrder.classList.add("hidden");
    wait.classList.remove("hidden");
    event.preventDefault();
}

function onError() {
    
}

function onUserMessageReceived(payload) {
    const order = JSON.parse(payload.body);
    console.log("message received");
    if (order.status === "accept") {
        const orderDiv = document.createElement('div');
        orderDiv.id = "order-div";
        orderDiv.classList.add("user-order");
        const info = document.createElement('label');
        info.textContent = order.taxiDriverName + "в пути";
        orderDiv.appendChild(info);
        buttonPage.appendChild(orderDiv);
        wait.classList.add("hidden");
    } else if (order.status === "end") {
        buttonOrder.classList.remove("hidden");
        document.querySelector("#order-div").remove();
    }

}   

function onTaxiMessageReceived(payload) {
    const order = JSON.parse(payload.body);
    
    if (order.status && order.status == "delete") {
        console.log(order.status);
        console.log(order.clientName);
        document.querySelector(`#${order.clientName}`).remove();
    } else if (clientNamePage === null) {
        const orderDiv = document.createElement('div');
        orderDiv.classList.add("taxi-form");
        const info = document.createElement('label');
        info.textContent = order.clientName;
        const button = document.createElement('button');
        button.textContent = "Принять";
        button.type = "button";
        button.addEventListener("click", () => acceptOrder(order.clientName), true);

        orderDiv.appendChild(info);
        orderDiv.appendChild(button);
        orderDiv.id = order.clientName;
        console.log(orderDiv);
        messagePage.appendChild(orderDiv);
    }
}

function acceptOrder(clientName) {
    if (stompClient && username) {
        stompClient.send("/app/taxi.acceptOrder",
            {},
            JSON.stringify({clientName: clientName, taxiDriverName: username})
        );

        stompClient.send("/app/taxi.deleteOrder",
            {},
            JSON.stringify({clientName: clientName})
        );

        let userDiv = document.getElementById(`${clientName}`);
        console.log(userDiv)
        userDiv.remove();
        messagePage.classList.add("hidden");
        for (const child of messagePage.children) {
            child.classList.add("hidden");
        }
        onTheWay.classList.remove("hidden");
        clientNamePage = clientName;
    }

}

function endOrderF(event) {
    if (stompClient && username) {
        stompClient.send("/app/taxi.endOrder",
            {},
            JSON.stringify({clientName: clientNamePage, taxiDriverName: username})
        );
        clientNamePage = null;
    }
    messagePage.classList.remove("hidden");
    for (const child of messagePage.children) {
        child.classList.remove("hidden");
    }
    onTheWay.classList.add("hidden");
    document.querySelector('#order-div').remove();
    event.preventDefault();
}


endOrder.addEventListener("click", endOrderF, true);
userForm.addEventListener("submit", connect, true);
buttonOrder.addEventListener("click", makeOrder, true);