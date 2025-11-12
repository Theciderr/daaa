(() => {
    const POLL_INTERVAL = 3000;

    function pollNotifications() {
        if (!window.appContext.userId) {
            return;
        }
        fetch(`${window.appContext.baseUrl}/notifications`, {
            headers: {
                "X-Requested-With": "XMLHttpRequest"
            }
        })
            .then(res => res.text())
            .then(parseNotificationXml)
            .catch(() => {
                /* silent */
            });
    }

    function parseNotificationXml(xmlText) {
        const doc = new DOMParser().parseFromString(xmlText, "application/xml");
        if (doc.querySelector("status")?.textContent !== "success") {
            return;
        }
        const notifications = doc.querySelectorAll("notification");
        if (!notifications.length) {
            return;
        }
        const ids = [];
        notifications.forEach(node => {
            const id = node.querySelector("id")?.textContent;
            const message = node.querySelector("message")?.textContent;
            if (id) {
                ids.push(id);
            }
            showNotification(message || "New alert");
        });
        if (ids.length) {
            acknowledgeNotifications(ids);
        }
    }

    function showNotification(message) {
        const panel = document.getElementById("notification-panel");
        if (!panel) {
            return;
        }
        const item = document.createElement("div");
        item.className = "notification-item";
        item.textContent = message;
        panel.appendChild(item);
        setTimeout(() => {
            item.classList.add("visible");
        }, 50);
        setTimeout(() => {
            item.classList.remove("visible");
            setTimeout(() => item.remove(), 500);
        }, 7000);
    }

    function acknowledgeNotifications(ids) {
        const params = new URLSearchParams();
        ids.forEach(id => params.append("notificationId", id));
        params.append("_csrf", window.appContext.csrfToken);
        fetch(`${window.appContext.baseUrl}/notifications`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-CSRF-Token": window.appContext.csrfToken
            },
            body: params.toString()
        }).catch(() => {
            /* ignore */
        });
    }

    document.addEventListener("DOMContentLoaded", () => {
        if (window.appContext.userId) {
            pollNotifications();
            setInterval(pollNotifications, POLL_INTERVAL);
        }
    });
})();

