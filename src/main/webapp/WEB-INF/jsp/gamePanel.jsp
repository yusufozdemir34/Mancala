<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<spring:url value="resources/css/style.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />

<body>
	<h1>Mancala Game</h1>
	<h2>Player Two Side</h2>

	<table>
		<tr>
			<td><a href="${pageContext.request.contextPath}/create"><img
					src="resources/images/CreateGame.jpg" /> </a></td>
			<td><a href="${pageContext.request.contextPath}/joingame"><img
					src="resources/images/JoinGame.jpg" /> </a></td>
		</tr>
	</table>

	<table>
		<tr>
			<%-- player two score --%>
			<td>
				<div class="pit middle">${pitStones[13]}</div> <img
				src="resources/images/leftScore.jpg" />
			</td>

			<%-- main board --%>
			<td>
				<table>
					<tr>
						<td><a href="${pageContext.request.contextPath}/input/12"><img
								src="resources/images/bottomBoard.jpg" />
								<div class="pit bottom">${pitStones[12]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/11"><img
								src="resources/images/bottomBoard.jpg" />
								<div class="pit bottom">${pitStones[11]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/10"><img
								src="resources/images/bottomBoard.jpg" />
								<div class="pit bottom">${pitStones[10]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/9"><img
								src="resources/images/bottomBoard.jpg" />
								<div class="pit bottom">${pitStones[9]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/8"><img
								src="resources/images/bottomBoard.jpg" />
								<div class="pit bottom">${pitStones[8]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/7"><img
								src="resources/images/bottomBoard.jpg" />
								<div class="pit bottom">${pitStones[7]}</div> </a></td>
					</tr>


					<tr>
						<td><a href="${pageContext.request.contextPath}/input/0"><img
								src="resources/images/topBoard.jpg" />
								<div class="pit top">${pitStones[0]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/1"><img
								src="resources/images/topBoard.jpg" />
								<div class="pit top">${pitStones[1]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/2"><img
								src="resources/images/topBoard.jpg" />
								<div class="pit top">${pitStones[2]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/3"><img
								src="resources/images/topBoard.jpg" />
								<div class="pit top">${pitStones[3]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/4"><img
								src="resources/images/topBoard.jpg" />
								<div class="pit top">${pitStones[4]}</div> </a></td>
						<td><a href="${pageContext.request.contextPath}/input/5"><img
								src="resources/images/topBoard.jpg" />
								<div class="pit top">${pitStones[5]}</div> </a></td>
					</tr>


				</table>
			</td>

			<%-- player one score --%>
			<td>
				<div class="pit middle">${pitStones[6]}</div> <img
				src="resources/images/rightScore.jpg" />
			</td>
		</tr>
	</table>
	<h2>Player One Side</h2>
	<br>
	<tr>
		<p>You are: ${youAre}</p>
	</tr>
	<tr>
		<p>Current Player: ${currentPlayer}</p>
	</tr>
	<tr>
		<p>Game Status: ${gameStatus}</p>
	</tr>
	<tr>
		<p>Winner is : ${gameMessage}</p>
	</tr>
	<tr>
		<p>Game id is : ${gameId}</p>
	</tr>

	<tr>
		<p>Error Message: ${errorMessage}</p>
	</tr>

	<c:choose>
		<c:when test="${gameMessage != null}">
			<h2 class="message">${gameMessage}</h2>
		</c:when>
		<c:otherwise>
			<p>Current Player: ${currentPlayer}</p>
		</c:otherwise>
	</c:choose>
</body>
</html>