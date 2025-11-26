/*
  # Create M-Kulima Database Schema

  1. New Tables
    - `farmers`: Store farmer profiles and farm information
    - `weather_alerts`: Weather and climate risk alerts by county
    - `pest_alerts`: Pest and disease alerts
    - `daily_tasks`: Daily tasks assigned to farmers
    - `crop_prices`: Market price information
    - `diagnosis_results`: AI diagnosis history

  2. Security
    - Enable RLS on all tables
    - Add policies for authenticated user access

  3. Notes
    - Offline-first design stores local knowledge base
    - Real-time sync for weather, prices, and alerts
*/

CREATE TABLE IF NOT EXISTS farmers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
  name TEXT NOT NULL,
  location TEXT NOT NULL,
  county TEXT NOT NULL,
  main_crop TEXT NOT NULL,
  farm_size_hectares NUMERIC NOT NULL,
  phone_number TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS weather_alerts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  county TEXT NOT NULL,
  temperature INTEGER NOT NULL,
  condition TEXT NOT NULL,
  description TEXT NOT NULL,
  risk_level TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS pest_alerts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  county TEXT NOT NULL,
  pest_name TEXT NOT NULL,
  severity TEXT NOT NULL,
  recommendation TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS daily_tasks (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  farmer_id UUID NOT NULL REFERENCES farmers(id) ON DELETE CASCADE,
  title TEXT NOT NULL,
  description TEXT NOT NULL,
  completed BOOLEAN DEFAULT false,
  due_date DATE NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS crop_prices (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  crop_name TEXT NOT NULL,
  location TEXT NOT NULL,
  price NUMERIC NOT NULL,
  currency TEXT DEFAULT 'KES',
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS diagnosis_results (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  farmer_id UUID NOT NULL REFERENCES farmers(id) ON DELETE CASCADE,
  photo_url TEXT NOT NULL,
  diagnosis TEXT NOT NULL,
  confidence NUMERIC NOT NULL,
  recommendations TEXT[] NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

ALTER TABLE farmers ENABLE ROW LEVEL SECURITY;
ALTER TABLE daily_tasks ENABLE ROW LEVEL SECURITY;
ALTER TABLE diagnosis_results ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Farmers can read their own profile"
  ON farmers FOR SELECT
  TO authenticated
  USING (auth.uid() = user_id);

CREATE POLICY "Farmers can update their own profile"
  ON farmers FOR UPDATE
  TO authenticated
  USING (auth.uid() = user_id)
  WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Farmers can create their profile"
  ON farmers FOR INSERT
  TO authenticated
  WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Farmers can read their own tasks"
  ON daily_tasks FOR SELECT
  TO authenticated
  USING (
    EXISTS (
      SELECT 1 FROM farmers WHERE farmers.id = farmer_id AND farmers.user_id = auth.uid()
    )
  );

CREATE POLICY "Farmers can update their own tasks"
  ON daily_tasks FOR UPDATE
  TO authenticated
  USING (
    EXISTS (
      SELECT 1 FROM farmers WHERE farmers.id = farmer_id AND farmers.user_id = auth.uid()
    )
  )
  WITH CHECK (
    EXISTS (
      SELECT 1 FROM farmers WHERE farmers.id = farmer_id AND farmers.user_id = auth.uid()
    )
  );

CREATE POLICY "Farmers can read their diagnosis history"
  ON diagnosis_results FOR SELECT
  TO authenticated
  USING (
    EXISTS (
      SELECT 1 FROM farmers WHERE farmers.id = farmer_id AND farmers.user_id = auth.uid()
    )
  );

CREATE POLICY "Public can read weather alerts"
  ON weather_alerts FOR SELECT
  USING (true);

CREATE POLICY "Public can read pest alerts"
  ON pest_alerts FOR SELECT
  USING (true);

CREATE POLICY "Public can read crop prices"
  ON crop_prices FOR SELECT
  USING (true);

CREATE INDEX farmers_user_id_idx ON farmers(user_id);
CREATE INDEX farmers_county_idx ON farmers(county);
CREATE INDEX daily_tasks_farmer_id_idx ON daily_tasks(farmer_id);
CREATE INDEX diagnosis_results_farmer_id_idx ON diagnosis_results(farmer_id);
CREATE INDEX weather_alerts_county_idx ON weather_alerts(county);
CREATE INDEX pest_alerts_county_idx ON pest_alerts(county);
CREATE INDEX crop_prices_crop_name_idx ON crop_prices(crop_name);
