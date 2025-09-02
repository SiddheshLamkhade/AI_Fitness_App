import React, { useState } from 'react'
import './App.css'
import ActivityForm from './components/ActivityForm.jsx'
import ActivitiesList from './components/ActivitiesList.jsx'
import { AppBar, Box, Container, CssBaseline, Toolbar, Typography } from '@mui/material'

function App() {
  const [refreshKey, setRefreshKey] = useState(0)

  const onActivityAdded = () => setRefreshKey((k) => k + 1)

  return (
    <>
      <CssBaseline />
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div">Fitness App</Typography>
        </Toolbar>
      </AppBar>
      <Container maxWidth="md">
        <Box sx={{ my: 4 }}>
          <Typography variant="h5" gutterBottom>Track Activity</Typography>
          <ActivityForm onActivityAdded={onActivityAdded} />
        </Box>
        <Box sx={{ my: 4 }}>
          <Typography variant="h5" gutterBottom>Activities</Typography>
          <ActivitiesList refreshKey={refreshKey} />
        </Box>
      </Container>
    </>
  )
}

export default App
