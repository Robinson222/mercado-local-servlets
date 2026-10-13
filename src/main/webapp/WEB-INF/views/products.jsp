<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="es"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Mercado Local | Productos</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/site.css"></head>
<body><main class="container">
<header class="header"><div><p class="eyebrow">MERCADO LOCAL · MÓDULO WEB</p><h1>Catálogo de productos</h1><p>Consulta y administra los productos de la tienda.</p></div><a class="button muted" href="${pageContext.request.contextPath}/products">Actualizar lista</a></header>
<c:if test="${not empty flashMessage}"><div class="notice success" role="status"><c:out value="${flashMessage}"/></div></c:if>
<c:if test="${not empty errorMessage}"><div class="notice error" role="alert"><c:out value="${errorMessage}"/></div></c:if>
<section class="panel"><h2>Registrar producto</h2>
<form method="post" action="${pageContext.request.contextPath}/products" class="fields">
<input type="hidden" name="action" value="create">
<label>Nombre<input name="name" maxlength="120" required></label>
<label>Descripción<input name="description" maxlength="500"></label>
<label>Precio<input name="price" type="number" min="0" step="0.01" required></label>
<label>Unidades<input name="stock" type="number" min="0" step="1" required></label>
<button class="button primary" type="submit">Guardar producto</button></form></section>
<section class="catalog"><div class="section-heading"><h2>Productos existentes</h2><span><c:out value="${products.size()}"/> registros</span></div>
<c:choose><c:when test="${empty products}"><div class="panel empty">No hay productos. Registra el primero con el formulario anterior.</div></c:when><c:otherwise><div class="product-list">
<c:forEach var="product" items="${products}"><article class="product-card">
<div class="product-title"><div><span class="id">ID <c:out value="${product.id}"/></span><h3><c:out value="${product.name}"/></h3></div><strong>$ <c:out value="${product.price}"/></strong></div>
<p class="description"><c:out value="${product.description}"/></p><p class="stock">Inventario: <c:out value="${product.stock}"/> unidades</p>
<form method="post" action="${pageContext.request.contextPath}/products" class="fields edit-fields">
<input type="hidden" name="action" value="update"><input type="hidden" name="id" value="<c:out value='${product.id}'/>">
<label>Nombre<input name="name" maxlength="120" required value="<c:out value='${product.name}'/>" aria-label="Nombre"></label>
<label>Descripción<input name="description" maxlength="500" value="<c:out value='${product.description}'/>" aria-label="Descripción"></label>
<label>Precio<input name="price" type="number" min="0" step="0.01" required value="<c:out value='${product.price}'/>" aria-label="Precio"></label>
<label>Unidades<input name="stock" type="number" min="0" step="1" required value="<c:out value='${product.stock}'/>" aria-label="Unidades"></label>
<button class="button muted" type="submit">Actualizar</button></form>
<form method="post" action="${pageContext.request.contextPath}/products" class="delete-form" onsubmit="return confirm('¿Eliminar este producto?');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<c:out value='${product.id}'/>">
<button class="button danger" type="submit">Eliminar producto</button></form></article></c:forEach></div></c:otherwise></c:choose></section>
<footer>Consulta mediante GET; altas, cambios y eliminaciones mediante POST.</footer></main></body></html>
