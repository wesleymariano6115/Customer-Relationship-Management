terraform {
  required_providers {
    railway = {
      source = "terraform-community-providers/railway"
      version = "0.5.1"
    }
  }
}

provider "railway" {
  token = var.railway_token
}


resource "railway_project" "backend_project" {
  name = "backend"
}

resource "railway_service" "backend_service" {
  project_id = railway_project.backend_project.id
  name       = "backend-api"
}

resource "railway_variable" "database_url" {
  service_id     = railway_service.backend_service.id
  environment_id = var.environment_id
  name           = "DATABASE_URL"
  value          = var.database_url
}