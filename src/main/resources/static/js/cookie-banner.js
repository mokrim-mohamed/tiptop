function acceptCookies() {
    document.getElementById("cookie-banner").style.display = "none";
    localStorage.setItem("cookiesAccepted", "true");
  }
  
  function rejectCookies() {
    document.getElementById("cookie-banner").style.display = "none";
    localStorage.setItem("cookiesAccepted", "false");
  }
  
  window.onload = function() {
    if (!localStorage.getItem("cookiesAccepted")) {
      document.getElementById("cookie-banner").style.display = "flex";
    }
  };
  