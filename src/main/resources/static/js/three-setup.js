// Setup de la scène Three.js
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

// Ajoute un objet 3D (ex: roue tournante)
const geometry = new THREE.TorusGeometry(5, 2, 16, 100);
const material = new THREE.MeshStandardMaterial({ color: 0xffff00 });
const wheel = new THREE.Mesh(geometry, material);
scene.add(wheel);

const light = new THREE.AmbientLight(0x404040, 2);
scene.add(light);

camera.position.z = 20;

function animate() {
    requestAnimationFrame(animate);
    wheel.rotation.y += 0.01;
    renderer.render(scene, camera);
}
animate();
