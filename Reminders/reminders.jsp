<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Reminders</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body>

<div class="main">

    <!-- Reminders form -->
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form">
                    <h2 class="form-title">Reminders</h2>
                    <form method="post" action="Reminders" class="register-form" id="reminders-form">
                        <!-- Add your form fields here -->
                        <div class="form-group">
                            <label for="DrinkWater"><i class="zmdi zmdi-account material-icons-name"></i>Have you drank water?</label>
                            <input type="checkbox" name="DrinkWater" id="DrinkWater" value="1">
                        </div>
                        <div class="form-group">
                            <label for="Eatmeal"><i class="zmdi zmdi-account material-icons-name"></i>Have you eaten today?</label>
                            <input type="checkbox" name="Eatmeal" id="Eatmeal" value="1">
                        </div>
                        <div class="form-group">
                            <label for="BrushTeeth"><i class="zmdi zmdi-account material-icons-name"></i>Have you brushed your teeth?</label>
                            <input type="checkbox" name="BrushTeeth" id="BrushTeeth" value="1">
                        </div>
                        <div class="form-group">
                            <label for="Steps"><i class="zmdi zmdi-account material-icons-name"></i>Have you taken 10,000 Steps?</label>
                            <input type="checkbox" name="Steps" id="Steps" value="1">
                        </div>
                        <div class="form-group">
                            <label for="Exercise"><i class="zmdi zmdi-account material-icons-name"></i>Have you exercised today?</label>
                            <input type="checkbox" name="Exercise" id="Exercise" value="1">
                        </div>
                        <div class="form-group form-button">
                            <input type="submit" name="submit" id="submit" class="form-submit" value="Submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>

<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
	<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>