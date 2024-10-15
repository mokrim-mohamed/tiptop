var files = [
    "index.html",
    "css/index.css",
    "css/rgbd.css",
    "img/empty.png",
    "img/icon.png",
    "js/jquery-3.2.1.min.js",
    "js/main.js",
    "js/bootstrap.min.js",
    "js/service-worker"
  ];
  
  // Ajout de la racine
  files.push('./');
  
  var CACHE_NAME = 'amarillo-v13';
  
  self.addEventListener('activate', function(event) {
    console.log('[SW] Activate');
    event.waitUntil(
      caches.keys().then(function(cacheNames) {
        return Promise.all(
          cacheNames.map(function(cacheName) {
            if (CACHE_NAME.indexOf(cacheName) === -1) {
              console.log('[SW] Delete cache:', cacheName);
              return caches.delete(cacheName);
            }
          })
        );
      })
    );
  });
  
  self.addEventListener('install', function(event){
    console.log('[SW] Install');
    event.waitUntil(
      caches.open(CACHE_NAME).then(function(cache) {
        return cache.addAll(files);
      })
    );
  });
  
  self.addEventListener('fetch', function(event) {
    console.log('[SW] fetch ' + event.request.url);
    event.respondWith(
      caches.match(event.request).then(function(response){
        return response || fetch(event.request);
      })
    );
  });
  
  self.addEventListener('notificationclick', function(event) {
    console.log('On notification click: ', event);
    clients.openWindow('/');
  });
  