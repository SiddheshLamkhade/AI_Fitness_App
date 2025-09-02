import React, { useEffect, useState } from 'react'
import { getActivities } from '../services/api'
import { Box, CircularProgress, List, ListItem, ListItemText, Typography } from '@mui/material'

const ActivitiesList = ({ refreshKey = 0 }) => {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [items, setItems] = useState([])

  useEffect(() => {
    let isMounted = true
    const load = async () => {
      setLoading(true)
      setError('')
      try {
        const res = await getActivities()
        if (!isMounted) return
        setItems(Array.isArray(res.data) ? res.data : res.data?.content || [])
      } catch (e) {
        if (!isMounted) return
        setError(e?.message || 'Failed to load activities')
      } finally {
        if (isMounted) setLoading(false)
      }
    }
    load()
    return () => { isMounted = false }
  }, [refreshKey])

  if (loading) return <Box sx={{ display:'flex', justifyContent:'center', my: 2 }}><CircularProgress /></Box>
  if (error) return <Typography color="error" sx={{ my: 2 }}>{error}</Typography>
  if (!items.length) return <Typography sx={{ my: 2 }}>No activities yet.</Typography>

  return (
    <List>
      {items.map((a, idx) => (
        <ListItem key={a.id || idx} divider>
          <ListItemText primary={`${a.type || 'UNKNOWN'} • ${a.duration ?? '-'} min • ${a.caloriesBurned ?? '-'} kcal`} secondary={a.startTime || ''} />
        </ListItem>
      ))}
    </List>
  )
}

export default ActivitiesList
